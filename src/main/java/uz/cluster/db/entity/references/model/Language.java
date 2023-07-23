package uz.cluster.db.entity.references.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "r_languages")
public class Language {

    @Hidden
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private int id;

    @Column(name = "name"/*, columnDefinition = "t_nls"*/)
    private String name;

    @Column(name = "status")
    private String status;

    public static String getLanguage(int langId) {
        //1,'Русская','A');
        //2,'Узбекча','A');
        //3,'O''zbekcha','A');
        //4,'English','A');
        //5,'Karakalpak tili','A');
        switch (langId) {
            case 2:
                return "uz_kir";
            case 3:
                return "uz_lat";
            default:
                return "ru";
        }
    }

    public static int getLanguageId(String locate) {
        //1,'Русская','A');
        //2,'Узбекча','A');
        //3,'O''zbekcha','A');
        //4,'English','A');
        //5,'Karakalpak tili','A');
        switch (locate.toLowerCase()) {
            case "uz_kir":
                return 2;
            case "uz_lat":
                return 3;
            default:
                return 1;
        }
    }

    public String getLanguageChar() {
        return getLanguage(id);
    }
}
