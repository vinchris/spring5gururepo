package guru.springframework.spring5webapp;

import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.repositories.BookRepository;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest // performs a minimalistic autoconfiguration in which DataInitializer bean is not run ... Annotation that can be used in combination with @RunWith(SpringRunner.class) for a typical JPA test. Can be used when a test focuses only on JPA components.
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringBootJpaSpliceTest {
    @Autowired
    BookRepository bookRepository;

    //@Rollback(value = false) // rollback set to false is needed because SB always performs rollback on the end of a test, we could also do @Commit
    @Commit
    @Order(1)
    @Test
    public void testJpaTestSplice(){
        bookRepository.deleteAll();
        long countBefore = bookRepository.count();
        assertThat(countBefore).isZero();

        bookRepository.save(new Book("My New Test Book", "1009", "SelfPublishing"));

        long countAfter = bookRepository.count();

        assertThat(countBefore).isLessThan(countAfter);
    }

    @Order(2) // this annotation indicates that the context will run this method exactly after it ran the method with annotation "@Order(1)"
    @Test
    public void testJpaTestSpliceTransaction(){
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(1);
    }
}
