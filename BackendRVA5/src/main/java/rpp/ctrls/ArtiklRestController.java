package rpp.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rpp.reps.ArtiklRepository;
import rpp.jpa.Artikl;

@RestController
public class ArtiklRestController {
	
	@Autowired
	private ArtiklRepository artiklRepository;
	
	@GetMapping("artikl")
	public Collection<Artikl> getArtikli(){
		return artiklRepository.findAll(); 
	}
	
	@GetMapping("artiklId/{id}")
	public Artikl getArtikl(@PathVariable("id") Integer id) {
		return artiklRepository.getOne(id);
	}
	
	@GetMapping("artiklNaziv/{naziv}")
	public Collection<Artikl> getArtiklByNaziv(@PathVariable("naziv") String naziv){
		return artiklRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@DeleteMapping("artiklId/{id}")
	public ResponseEntity<Artikl> deleteArtikl(@PathVariable("id") Integer id){
		if(artiklRepository.existsById(id)) {
			artiklRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//insert - Ovde koristimo POST metodu
	@PostMapping("artikl")
	public ResponseEntity<Artikl> insertArtikl(@RequestBody Artikl artikl){
		if(artiklRepository.existsById(artikl.getId())) {
			return new ResponseEntity<> (HttpStatus.CONFLICT);
		}
		artiklRepository.save(artikl);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//update - Ovde koristimo PUT metodu
	@PutMapping("artikl")
	public ResponseEntity<Artikl> updateArtikl(@RequestBody Artikl artikl){
		if(artiklRepository.existsById(artikl.getId())) {
			artiklRepository.save(artikl);
			return new ResponseEntity<> (HttpStatus.OK);
		}
		return new ResponseEntity<> (HttpStatus.NO_CONTENT);
	}
	
}
