package com.mexpedition.web.controller;

import com.mexpedition.dao.ExpeditionDao;
import com.mexpedition.model.Expedition;
import com.mexpedition.web.exceptions.ExpeditionNotFoundException;
import com.mexpedition.web.exceptions.ImpossibleAjouterExpeditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ExpeditionController {

    @Autowired
    ExpeditionDao expeditionDao;

    @PostMapping(value = "/expedition")
    public ResponseEntity<Expedition> ajouterExpedition(@RequestBody Expedition expedition){
        Expedition nouvelleExpedition = expeditionDao.save(expedition);

        if(nouvelleExpedition == null) throw new ImpossibleAjouterExpeditionException("Impossible d'ajouter cette expedition");

        return new ResponseEntity<Expedition>(expedition, HttpStatus.CREATED);
    }

    @GetMapping(value = "/expedition/{id}")
    public Optional<Expedition> recupererUneExpedition(@PathVariable int id){

        Optional<Expedition> expedition = expeditionDao.findById(id);

        if(!expedition.isPresent()) throw new ExpeditionNotFoundException("Cette expedition n'existe pas");

        return expedition;
    }

    /*
     * Permet de mettre à jour une expedition existante.
     * save() mettra à jours uniquement les champs renseignés dans l'objet expedition reçu. Ainsi dans ce cas, comme le champs date dans "expedition" n'est
     * pas renseigné, la date précédemment enregistrée restera en place
     **/
    @PutMapping(value = "/expedition")
    public void updateExpedition(@RequestBody Expedition expedition) {

        expeditionDao.save(expedition);
    }
}
