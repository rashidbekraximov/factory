package uz.cluster.services;

public interface FormActions<T> {
    public long add(T formDao) throws Exception; //for adding

    public void save(T formDao) throws Exception; //for editing

    public void deleteByDocumentId(Long documentId); // form deleting
}
