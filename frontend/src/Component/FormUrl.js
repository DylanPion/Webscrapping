import React, { useState } from "react";
import axios from "axios";

function FormUrl() {
  const [url, setUrl] = useState("");
  const [isValidUrl, setIsValidUrl] = useState(true);

  const handleUrlChange = (e) => {
    const inputUrl = e.target.value;
    setUrl(inputUrl);
    // Utiliser une regex pour vérifier si l'URL est valide
    const urlRegex = /^(https?|ftp):\/\/[^\s/$.?#].[^\s]*$/i;
    setIsValidUrl(urlRegex.test(inputUrl));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!isValidUrl) {
      console.error("URL invalide.");
      return;
    }

    try {
      await axios.post("http://localhost:8080/scraped-data", {
        url,
      });

      // Une fois que la requête Axios est terminée avec succès, rechargez la page.
      window.location.reload();
    } catch (error) {
      console.error("Erreur lors de l'envoi au serveur :", error);
    }
  };

  return (
    <div className="content">
      <form className="formUrl" onSubmit={handleSubmit}>
        <label>Lien de l'URL à Scrapper :</label>
        <input
          type="text"
          value={url}
          onChange={handleUrlChange}
          placeholder="Entrez l'URL ici"
          style={{ borderColor: isValidUrl ? "initial" : "red" }}
        />
        <br />
        {!isValidUrl && <p style={{ color: "red" }}>URL invalide.</p>}
        <button type="submit">Envoyer</button>
      </form>
    </div>
  );
}

export default FormUrl;
