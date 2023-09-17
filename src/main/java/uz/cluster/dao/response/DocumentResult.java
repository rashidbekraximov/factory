package uz.cluster.dao.response;

public class DocumentResult {
    private String httpMethod;
    private long documentId;
    private int formId;
    private int responseCode;
    private String msg;

    public DocumentResult(int formId, String httpMethod, int responseCode, String msg) {
        this.httpMethod = httpMethod;
        this.documentId = -1;
        this.responseCode = responseCode;
        this.formId = formId;
        this.msg = msg;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }
}
