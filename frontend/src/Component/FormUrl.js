// FormUrl.js
import React, { useState } from "react";
import axios from "axios";

function FormUrl() {
  // Etat local pour stocker l'URL entrée par l'utilisateur
  const [url, setUrl] = useState("");

  const handleUrlChange = (e) => {
    // Mise à jour de l'état 'url' avec la nouvelle valeur du champ de saisie
    setUrl(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Envoie des données du formulaire au serveur en utilisant Axios
      const response = await axios.post("http://localhost:8080/scraped-data", {
        url,
      });
    } catch (error) {
      console.error("Erreur lors de l'envoi au serveur :", error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Lien de l'URL à Scrapper :
        <input
          type="text"
          value={url}
          onChange={handleUrlChange}
          placeholder="Entrez l'URL ici"
        />
      </label>
      <br />
      <button type="submit">Envoyer</button>
    </form>
  );
}

export default FormUrl;
