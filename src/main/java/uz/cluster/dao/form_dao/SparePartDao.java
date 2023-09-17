package uz.cluster.dao.form_dao;

import uz.cluster.entity.Document;
import uz.cluster.entity.forms.SparePart;

import java.io.Serializable;
import java.util.List;

public class SparePartDao extends Document implements Serializable {

    private List<SparePart> forms;

    public List<SparePart> getForms(){
        return forms;
    }

    public void setForms(List<SparePart> form){
        this.forms = form;
    }

    public SparePartDao(){
        super();
    }

    public SparePartDao(int formType){
        super(formType);
    }

    public SparePartDao(Document document, List<SparePart> forms){
        super(document);
        this.forms = forms;
    }
}
