package ua.od.whcrow.samples.spring6.boot_web.samples.web.rest;

import jakarta.annotation.Nonnull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.od.whcrow.samples.spring6.boot_web._commons.collections.FixedSizeHashMap;
import ua.od.whcrow.samples.spring6.boot_web._commons.security.CsrfIgnore;
import ua.od.whcrow.samples.spring6.boot_web._commons.util.Assert;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.NotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.UnprocessableEntityException;
import ua.od.whcrow.samples.spring6.boot_web.samples.SamplesConstants;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(HateoasSamplesRestController.REQUEST_PATH)
@CsrfIgnore
class HateoasSamplesRestController {
	
	static final String REQUEST_PATH = SamplesConstants.REQ_P_API_SAMPLES + "/hateoas";
	
	private final Map<Integer,Person> persons = new FixedSizeHashMap<>(10, Map.of(
			1, new Person(1, "Joanna", "Doe"),
			2, new Person(2, "John", "Doe")
	));
	
	@Nonnull
	private Iterable<Link> getDefaultLinks() {
		return List.of(
				linkTo(methodOn(getClass()).listPersons()).withRel("list"),
				linkTo(methodOn(getClass()).createPerson(null)).withRel("create")
		);
	}
	
	@Nonnull
	private Iterable<Link> getPersonLinks(@Nonnull Person person) {
		Assert.notNull(person, "person");
		return List.of(
				linkTo(methodOn(getClass()).readPerson(person.id())).withRel("read"),
				linkTo(methodOn(getClass()).updatePerson(person.id(), person)).withRel("update"),
				linkTo(methodOn(getClass()).deletePerson(person.id())).withRel("delete")
		);
	}
	
	@PostMapping
	EntityModel<Person> createPerson(@RequestBody Person person) {
		if (persons.containsKey(person.id())) {
			throw new UnprocessableEntityException("Person with ID {} already exists", Map.of("id", person.id()),
					person.id());
		}
		persons.put(person.id(), person);
		return EntityModel.of(person)
				.add(linkTo(methodOn(getClass()).createPerson(person)).withSelfRel())
				.add(getDefaultLinks())
				.add(getPersonLinks(person));
	}
	
	@GetMapping("/{id}")
	EntityModel<Person> readPerson(@PathVariable int id) {
		Person person = persons.get(id);
		if (person == null) {
			throw new NotFoundException("Person with ID {} is not found", id);
		}
		return EntityModel.of(person)
				.add(linkTo(methodOn(getClass()).readPerson(person.id())).withSelfRel())
				.add(getDefaultLinks())
				.add(getPersonLinks(person));
	}
	
	@PostMapping("/{id}")
	EntityModel<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {
		if (id != person.id()) {
			throw new UnprocessableEntityException("Person ID {} does not match to ID {} specified in request path",
					Map.of("id", person.id()), person.id(), id);
		}
		if (!persons.containsKey(id)) {
			throw new NotFoundException("Person with ID {} is not found", id);
		}
		persons.put(id, person);
		return EntityModel.of(person)
				.add(linkTo(methodOn(getClass()).updatePerson(id, person)).withSelfRel())
				.add(getDefaultLinks())
				.add(getPersonLinks(person));
	}
	
	@DeleteMapping("/{id}")
	EntityModel<Person> deletePerson(@PathVariable int id) {
		Person person = persons.get(id);
		if (person == null) {
			throw new NotFoundException("Person with ID {} is not found", id);
		}
		persons.remove(id);
		return EntityModel.of(person)
				.add(getDefaultLinks());
	}
	
	@GetMapping
	CollectionModel<Person> listPersons() {
		return CollectionModel.of(persons.values())
				.add(linkTo(methodOn(getClass()).listPersons()).withSelfRel())
				.add(getDefaultLinks());
	}
	
	private record Person(int id, String firstName, String lastName) {}
	
}
