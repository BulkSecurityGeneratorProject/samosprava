package frido.samosprava.web.rest;

import frido.samosprava.domain.Person;
import frido.samosprava.repository.PersonRepository;
import frido.samosprava.service.PersonService;
import frido.samosprava.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link frido.samosprava.domain.Person}.
 */
@RestController
@RequestMapping("/api")
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    private static final String ENTITY_NAME = "person";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonRepository personRepository;
    private final PersonService personService;

    public PersonResource(PersonRepository personRepository, PersonService personService) {
        this.personRepository = personRepository;
        this.personService = personService;
    }

    /**
     * {@code POST  /people} : Create a new person.
     *
     * @param person the person to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new person, or with status {@code 400 (Bad Request)} if the person has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/people")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) throws URISyntaxException {
        log.debug("REST request to save Person : {}", person);
        if (person.getId() != null) {
            throw new BadRequestAlertException("A new person cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Person result = personRepository.save(person);
        return ResponseEntity.created(new URI("/api/people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /people} : Updates an existing person.
     *
     * @param person the person to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated person,
     * or with status {@code 400 (Bad Request)} if the person is not valid,
     * or with status {@code 500 (Internal Server Error)} if the person couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/people")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) throws URISyntaxException {
        log.debug("REST request to update Person : {}", person);
        if (person.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Person result = personRepository.save(person);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, person.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /people} : get all the people.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of people in body.
     */
    @GetMapping("/people")
    public List<Person> getAllPeople(
            @RequestParam(required = false) String councilId,
            @RequestParam(required = false, defaultValue = "false") Boolean council,
            @RequestParam(required = false, defaultValue = "false") Boolean duty,
            @RequestParam(required = false, defaultValue = "false") Boolean department
            ) {
        log.debug("REST request to get all People");
        if (councilId != null) {
            return personService.findAllPersonsByCouncilId(councilId, council, duty, department);
        }
        return personRepository.findAll();
    }

    /**
     * {@code GET  /people/:id} : get the "id" person.
     *
     * @param id the id of the person to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the person, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/people/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable String id) {
        log.debug("REST request to get Person : {}", id);
        Optional<Person> person = personRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(person);
    }

    /**
     * {@code DELETE  /people/:id} : delete the "id" person.
     *
     * @param id the id of the person to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable String id) {
        log.debug("REST request to delete Person : {}", id);
        personRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
