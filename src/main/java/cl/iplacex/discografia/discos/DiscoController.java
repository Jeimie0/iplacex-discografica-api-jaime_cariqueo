package cl.iplacex.discografia.discos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.iplacex.discografia.artistas.IArtistaRepository;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository;

    @Autowired
    private IArtistaRepository artistaRepository;


    // POST - Crear un disco
    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(
            @RequestBody Disco disco) {

        try {

            if (!artistaRepository.existsById(disco.idArtista)) {
                return new ResponseEntity<>(
                    "Artista no encontrado",
                    HttpStatus.NOT_FOUND
                );
            }

            Disco discoInsertado = discoRepository.insert(disco);

            return new ResponseEntity<>(
                discoInsertado,
                HttpStatus.CREATED
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    // GET - Listar todos los discos
    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {

        List<Disco> discos = discoRepository.findAll();

        return new ResponseEntity<>(
            discos,
            HttpStatus.OK
        );
    }


    // GET - Buscar un disco por ID
    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetDiscoRequest(
            @PathVariable String id) {

        try {

            Disco disco = discoRepository
                .findById(id)
                .orElse(null);

            if (disco == null) {
                return new ResponseEntity<>(
                    "Disco no encontrado",
                    HttpStatus.NOT_FOUND
                );
            }

            return new ResponseEntity<>(
                disco,
                HttpStatus.OK
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    // GET - Listar discos de un artista
    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(
            @PathVariable String id) {

        List<Disco> discos =
            discoRepository.findDiscosByIdArtista(id);

        return new ResponseEntity<>(
            discos,
            HttpStatus.OK
        );
    }
}