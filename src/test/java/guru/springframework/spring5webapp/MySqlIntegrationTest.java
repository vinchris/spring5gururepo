package guru.springframework.spring5webapp;

import guru.springframework.spring5webapp.repositories.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
@DataJpaTest // performs a minimalistic autoconfiguration in which DataInitializer bean is not run ...
// Annotation @DataJpaTest that can be used in combination with @RunWith(SpringRunner.class) for a typical JPA test. Can be used when a test focuses only on JPA components. By default it uses the H2 datasource
@ComponentScan(basePackages = {"guru.springframework.spring5webapp.bootstrap"}) // it searches for all components in that specific package and brings that component into the context if it has CLIRunner, it will run
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MySqlIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    public void testMySql(){
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);



    }
}
