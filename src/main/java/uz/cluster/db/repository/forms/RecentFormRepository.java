package uz.cluster.db.repository.forms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.cluster.db.entity.forms.RecentForm;

import java.util.List;
import java.util.Optional;

public interface RecentFormRepository extends JpaRepository<RecentForm, Integer> {
    @Query("select s from RecentForm s where s.user.id = ?1")
    List<RecentForm> findAllByUserId(int user_id);

    @Query("select s from RecentForm s where s.form.id = ?1 and s.user.id = ?2")
    Optional<RecentForm> findByFormIdAndUserId(int form_id, int user_id);
}
