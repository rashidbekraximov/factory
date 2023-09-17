package uz.cluster.services.form_services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.cluster.annotation.CheckPermission;
import uz.cluster.dao.form_dao.SparePartDao;
import uz.cluster.entity.Document;
import uz.cluster.entity.forms.SparePart;
import uz.cluster.entity.references.model.Measurement;
import uz.cluster.entity.references.model.MechanicalProduct;
import uz.cluster.enums.auth.Action;
import uz.cluster.enums.forms.FormEnum;
import uz.cluster.repository.forms.SparePartRepository;
import uz.cluster.repository.references.MeasurementRepository;
import uz.cluster.repository.references.MechanicalProductRepository;
import uz.cluster.services.FormActions;
import uz.cluster.util.CurrencyExchangeRate;
import uz.cluster.util.LanguageManager;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SparePartService implements FormActions<SparePartDao> {

    private final SparePartRepository formRepository;

    private final DocumentRepository documentRepository;

    private final MechanicalProductRepository mechanicalProductRepository;

    private final MeasurementRepository measurementRepository;

    @CheckPermission(form = FormEnum.SPARE_PART, permission = Action.CAN_ADD)
    @Transactional
    @Override
    public long add(SparePartDao formDao) throws Exception {
        double amount = 0, valueAddedTax = 0;
        for (SparePart item : formDao.getForms()) {
            amount += item.getAmount();
            if (item.isCalcValueAddedTax()) {
                valueAddedTax += item.getValueAddedTax();
            } else {
                item.setValueAddedTax(0);
            }
            item.setId(0);
            item.setAllAmount(item.getAmount() + item.getValueAddedTax());

            if (item.getMeasurementId() == 0 || item.getMechanicalProductId() == 0) {
                throw new Exception(LanguageManager.getLangMessage("no_data_submitted"));
            }

            Optional<Measurement> optionalMeasurement = measurementRepository.findById(item.getMeasurementId());
            optionalMeasurement.ifPresent(item::setMeasurement);

            Optional<MechanicalProduct> optionalMechanicalProduct = mechanicalProductRepository.findById(item.getMechanicalProductId());
            optionalMechanicalProduct.ifPresent(item::setMechanicalProduct);
        }

        Document doc = new Document(formDao.getDocumentCode(), formDao.getDocumentDate(), formDao.getSeasonId(),
                FormEnum.SPARE_PART.getValue(), null, formDao.getDepartmentId(), amount, valueAddedTax, formDao.getDescription());
        CurrencyExchangeRate.calculateDollarByDate(doc);
        documentRepository.save(doc);

        for (SparePart item : formDao.getForms()) {
            item.setDocumentId(doc.getDocumentId());
        }

        formRepository.saveAll(formDao.getForms());
//        transactService.createTransacts(doc);
        return doc.getDocumentId();
    }

    public List<SparePart> getAll() {
        return formRepository.findAll();
    }


    public SparePartDao getByDocumentId(long documentId) {
        Document doc = documentRepository.getByDocumentId(documentId);
        List<SparePart> formList = formRepository.getByDocumentId(documentId);
        return new SparePartDao(doc, formList);
    }

    @CheckPermission(form = FormEnum.SPARE_PART, permission = Action.CAN_EDIT)
    @Transactional
    @Override
    public void save(SparePartDao formDao) throws Exception {
        Document doc = new Document(formDao);

        double amount = 0, valueAddedTax = 0;
        for (SparePart item : formDao.getForms()) {
            amount += item.getAmount();
            if (item.isCalcValueAddedTax()) {
                valueAddedTax += item.getValueAddedTax();
            } else {
                item.setValueAddedTax(0);
            }
            item.setAllAmount(item.getAmount() + item.getValueAddedTax());
            item.setDocumentId(doc.getDocumentId());

            Optional<Measurement> optionalMeasurement = measurementRepository.findById(item.getMeasurementId());
            optionalMeasurement.ifPresent(item::setMeasurement);

            Optional<MechanicalProduct> optionalMechanicalProduct = mechanicalProductRepository.findById(item.getMechanicalProductId());
            optionalMechanicalProduct.ifPresent(item::setMechanicalProduct);
        }

        doc.setAmount(amount);
        doc.setValueAddedTax(valueAddedTax);
        CurrencyExchangeRate.calculateDollarByDate(doc);
        doc.setCostGroupId(0);
        documentRepository.save(doc);

        List<SparePart> oldForms = formRepository.getByDocumentId(doc.getDocumentId());


        for (SparePart oldForm : oldForms) {
            long formId = oldForm.getId();
            boolean isHas = false;
            for (SparePart item : formDao.getForms()) {
                if (formId == item.getId()) {
                    isHas = true;
                    break;
                }
            }
            if (isHas)
                continue;
            formRepository.deleteById(formId);
        }

        formRepository.saveAll(formDao.getForms());
//        transactService.createTransacts(doc);
    }

    @CheckPermission(form = FormEnum.SPARE_PART, permission = Action.CAN_DELETE)
    @Transactional
    @Override
    public void deleteByDocumentId(Long documentId) {
        formRepository.deleteByDocumentId(documentId);
        documentRepository.deleteById(documentId);
//        transactService.deleteTransactsByDocumentId(documentId);
    }
}