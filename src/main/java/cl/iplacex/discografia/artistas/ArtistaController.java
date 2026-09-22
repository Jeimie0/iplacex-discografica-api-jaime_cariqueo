package cl.iplacex.discografia.artistas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepository;


    // POST - Crear un artista
    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleInsertArtistaRequest(
            @RequestBody Artista artista) {

        try {

            Artista artistaInsertado = artistaRepository.insert(artista);

            return new ResponseEntity<>(
                artistaInsertado,
                HttpStatus.CREATED
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    // GET - Listar todos los artistas
    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {

        List<Artista> artistas = artistaRepository.findAll();

        return new ResponseEntity<>(
            artistas,
            HttpStatus.OK
        );
    }


    // GET - Buscar un artista por ID
    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetArtistaRequest(
            @PathVariable String id) {

        try {

            Artista artista = artistaRepository
                .findById(id)
                .orElse(null);

            if (artista == null) {
                return new ResponseEntity<>(
                    "Artista no encontrado",
                    HttpStatus.NOT_FOUND
                );
            }

            return new ResponseEntity<>(
                artista,
                HttpStatus.OK
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    // PUT - Actualizar un artista
    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleUpdateArtistaRequest(
            @PathVariable String id,
            @RequestBody Artista artista) {

        try {

            if (!artistaRepository.existsById(id)) {
                return new ResponseEntity<>(
                    "Artista no encontrado",
                    HttpStatus.NOT_FOUND
                );
            }

            artista._id = id;

            Artista artistaActualizado =
                artistaRepository.save(artista);

            return new ResponseEntity<>(
                artistaActualizado,
                HttpStatus.OK
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    // DELETE - Eliminar un artista
    @DeleteMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleDeleteArtistaRequest(
            @PathVariable String id) {

        try {

            if (!artistaRepository.existsById(id)) {
                return new ResponseEntity<>(
                    "Artista no encontrado",
                    HttpStatus.NOT_FOUND
                );
            }

            artistaRepository.deleteById(id);

            return new ResponseEntity<>(
                "Artista eliminado correctamente",
                HttpStatus.OK
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}