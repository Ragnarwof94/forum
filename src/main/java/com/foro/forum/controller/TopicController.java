package com.foro.forum.controller;

import com.foro.forum.dto.TopicCreationDTO; // DTO para crear tópicos
import com.foro.forum.dto.TopicResponseDTO; // DTO para responder con tópicos
import com.foro.forum.dto.TopicUpdateDTO; // DTO para actualizar tópicos
import com.foro.forum.model.Topic; // Entidad Topic
import com.foro.forum.model.User; // Entidad User
import com.foro.forum.repository.TopicRepository; // Repositorio de tópicos
import com.foro.forum.repository.UserRepository; // Repositorio de usuarios
import jakarta.validation.Valid; // Para validación de DTOs
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // Para construir respuestas HTTP
import org.springframework.transaction.annotation.Transactional; // Para manejar transacciones de base de datos
import org.springframework.web.bind.annotation.*; // Anotaciones REST
import org.springframework.web.util.UriComponentsBuilder; // Para construir URIs

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController // Marca esta clase como un controlador REST
@RequestMapping("/topics") // Mapea todas las peticiones a /topics
public class TopicController {

    @Autowired
    private TopicRepository topicRepository; // Se inyecta el repositorio de tópicos
    @Autowired
    private UserRepository userRepository; // Se inyecta el repositorio de usuarios

    @GetMapping // Maneja peticiones GET a /topics (listar todos los tópicos)
    public ResponseEntity<List<TopicResponseDTO>> listTopics() {
        // Obtiene todos los tópicos de la base de datos, los mapea a TopicResponseDTO y los devuelve
        List<TopicResponseDTO> topics = topicRepository.findAll().stream()
                .map(TopicResponseDTO::new) // Convierte cada Topic a TopicResponseDTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(topics); // Devuelve la lista con status 200 OK
    }

    @GetMapping("/{id}") // Maneja peticiones GET a /topics/{id} (detallar un tópico por ID)
    public ResponseEntity<TopicResponseDTO> getTopicById(@PathVariable Long id) {
        return topicRepository.findById(id)
                .map(TopicResponseDTO::new) // Convierte a DTO si se encuentra
                .map(ResponseEntity::ok) // Si se encuentra, devuelve 200 OK con el DTO
                .orElse(ResponseEntity.notFound().build()); // Si no se encuentra, devuelve 404 Not Found
    }

    @PostMapping // Maneja peticiones POST a /topics (crear un nuevo tópico)
    @Transactional // Marca el método como transaccional (rollback en caso de error)
    public ResponseEntity<TopicResponseDTO> createTopic(@RequestBody @Valid TopicCreationDTO topicCreationDTO,
                                                        UriComponentsBuilder uriComponentsBuilder) {
        // Buscar el usuario que crea el tópico por su ID
        Optional<User> optionalUser = userRepository.findById(topicCreationDTO.userId());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Si el usuario no existe, devuelve 400 Bad Request
        }
        User author = optionalUser.get(); // Obtiene el objeto User

        // Crea una nueva instancia de Topic a partir del DTO y el autor
        Topic topic = new Topic(topicCreationDTO.title(), topicCreationDTO.message(), topicCreationDTO.courseName(), author);
        topicRepository.save(topic); // Guarda el tópico en la base de datos

        // Construye la URI del nuevo recurso creado para la respuesta 201 Created
        URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(url).body(new TopicResponseDTO(topic)); // Devuelve 201 Created con el DTO del tópico
    }

    @PutMapping("/{id}") // NUEVO MÉTODO: Maneja peticiones PUT a /topics/{id} (actualizar un tópico)
    @Transactional
    public ResponseEntity<TopicResponseDTO> updateTopic(@PathVariable Long id, @RequestBody @Valid TopicUpdateDTO topicUpdateDTO) {
        // Busca el tópico por su ID
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        if (optionalTopic.isEmpty()) {
            return ResponseEntity.notFound().build(); // Si no existe, devuelve 404 Not Found
        }

        Topic topic = optionalTopic.get();

        // Actualiza los campos del tópico si se proporcionan en el DTO de actualización
        // (La lógica de actualización está en el modelo Topic)
        topic.updateTopic(topicUpdateDTO.title(), topicUpdateDTO.message(), topicUpdateDTO.courseName());

        topicRepository.save(topic); // Guarda los cambios en la base de datos
        return ResponseEntity.ok(new TopicResponseDTO(topic)); // Devuelve 200 OK con el tópico actualizado
    }


    @DeleteMapping("/{id}") // Maneja peticiones DELETE a /topics/{id} (eliminar un tópico por ID)
    @Transactional // Marca el método como transaccional
    public ResponseEntity deleteTopic(@PathVariable Long id) {
        // Verifica si el tópico existe antes de intentar eliminarlo
        if (!topicRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // Si no existe, devuelve 404 Not Found
        }
        topicRepository.deleteById(id); // Elimina el tópico
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content para indicar eliminación exitosa
    }
}
