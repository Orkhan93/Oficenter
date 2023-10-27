package az.digitalhands.oficenter.repository;

import az.digitalhands.oficenter.domain.About;
import az.digitalhands.oficenter.wrapper.AboutWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AboutRepository extends JpaRepository<About, Long> {
    List<AboutWrapper> getAllAbout();
}
