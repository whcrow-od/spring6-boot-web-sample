package ua.od.whcrow.samples.spring6.boot_web.samples.web.rest;

import jakarta.annotation.Nonnull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.od.whcrow.samples.spring6.boot_web._commons.collections.FixedSizeHashMap;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.NotFoundException;
import ua.od.whcrow.samples.spring6.boot_web._commons.web.exceptions.UnprocessableEntityException;
import ua.od.whcrow.samples.spring6.boot_web.samples.SamplesConstants;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(SamplesConstants.REQ_P_API_SAMPLES + "/hateoas")
class HateoasSamplesRestController {
	
	private final Map<Integer,Person> persons = new FixedSizeHashMap<>(10, Map.of(
			1, new Person(1, "Joanna", "Doe"),
			2, new Person(2, "John", "Doe")
	));
	
	@Nonnull
	private EntityModel<Person> createPersonModel(@Nonnull Person person, WebMvcLinkBuilder self) {
		EntityModel<Person> entityModel = EntityModel.of(person);
		entityModel.add(self.withSelfRel());
		entityModel.add(linkTo(methodOn(getClass()).createPerson(person)).withRel("create"));
		entityModel.add(linkTo(methodOn(getClass()).readPerson(person.id())).withRel("read"));
		entityModel.add(linkTo(methodOn(getClass()).updatePerson(person.id(), person)).withRel("update"));
		entityModel.add(linkTo(methodOn(getClass()).deletePerson(person.id())).withRel("delete"));
		entityModel.add(linkTo(methodOn(getClass()).listPersons()).withRel("list"));
		return entityModel;
	}
	
	@PostMapping
	EntityModel<Person> createPerson(@RequestBody Person person) {
		persons.put(person.id(), person);
		return createPersonModel(person, linkTo(methodOn(getClass()).createPerson(person)));
	}
	
	@GetMapping("/{id}")
	EntityModel<Person> readPerson(@PathVariable int id) {
		Person person = persons.get(id);
		if (person == null) {
			throw new NotFoundException("Person with ID {} is not found", id);
		}
		return createPersonModel(person, linkTo(methodOn(getClass()).readPerson(person.id())));
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
		return createPersonModel(person, linkTo(methodOn(getClass()).updatePerson(id, person)));
	}
	
	@DeleteMapping("/{id}")
	EntityModel<Person> deletePerson(@PathVariable int id) {
		Person person = persons.get(id);
		if (person == null) {
			throw new NotFoundException("Person with ID {} is not found", id);
		}
		return createPersonModel(person, linkTo(methodOn(getClass()).deletePerson(id)));
	}
	
	@GetMapping
	CollectionModel<Person> listPersons() {
		return CollectionModel.of(persons.values())
				.add(linkTo(methodOn(getClass()).listPersons()).withSelfRel());
	}
	
	private record Person(int id, String firstName, String lastName) {}
	
}
