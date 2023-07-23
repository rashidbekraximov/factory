package uz.cluster.db.composite_keys;


import uz.cluster.db.entity.references.model.Form;

import java.io.Serializable;

public class FormDao extends Form implements Serializable {
    String ru;
    String uzLat;
    String uzCl;

    public void init() {
        ru = getName().getRu();
        uzLat = getName().getUz_lat();
        uzCl = getName().getUz_cl();
    }

    public void prepare() {
        getName().setRu(ru);
        getName().setUz_lat(uzLat);
        getName().setUz_cl(uzCl);
    }

    public String getRu() {
        return ru;
    }

    public void setRu(String ru) {
        this.ru = ru;
    }

    public String getUzLat() {
        return uzLat;
    }

    public void setUzLat(String uzLat) {
        this.uzLat = uzLat;
    }

    public String getUzCl() {
        return uzCl;
    }

    public void setUzCl(String uzCl) {
        this.uzCl = uzCl;
    }

    public FormDao() {
        super();
    }
}
