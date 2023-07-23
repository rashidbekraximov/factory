package uz.cluster.db.repository.forms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.cluster.db.entity.forms.StaredForm;

import java.util.List;
import java.util.Optional;

public interface StaredFormRepository extends JpaRepository<StaredForm, Integer> {
    @Query("select s from StaredForm s where s.user.id = ?1")
    List<StaredForm> findAllByUserId(int user_id);

    @Query("select s from StaredForm s where s.form.id = ?1 and s.user.id = ?2")
    Optional<StaredForm> findByFormIdAndUserId(int form_id, int user_id);
}
