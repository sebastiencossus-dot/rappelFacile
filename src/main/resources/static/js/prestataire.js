function addProfession() {
    const container = document.getElementById("profession-container");

    const div = document.createElement("div");
    div.classList.add("profession-item");

    div.innerHTML = `
        <select name="professionIds">
            <option value="">-- Choisir une profession --</option>
            ${document.querySelector('.profession-item select').innerHTML}
        </select>
        <button type="button" onclick="this.parentElement.remove()">Supprimer</button>
    `;

    container.appendChild(div);
}

function addAdresse() {
    const container = document.getElementById("adresse-container");

    const div = document.createElement("div");
    div.classList.add("adresse-item");

    div.innerHTML = `
        <input type="text" name="rue" placeholder="Rue"/>
        <input type="text" name="numero" placeholder="Numéro"/>
        <input type="text" name="ville" placeholder="Ville"/>
        <input type="text" name="codePostal" placeholder="Code postal"/>
        <button type="button" onclick="this.parentElement.remove()">Supprimer</button>
    `;

    container.appendChild(div);
}