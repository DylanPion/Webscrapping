# Webscrapping

En Spring Boot, l'annotation @Data fait partie du projet Lombok.
Lombok est une bibliothèque Java qui permet de réduire la quantité de code redondant et verbeux dans vos classes en générant automatiquement certains éléments, tels que les getters, les setters, les méthodes equals(), hashCode(), etc.

L'annotation @Data de Lombok est couramment utilisée pour marquer une classe en tant que classe de données (data class). Elle génère automatiquement les méthodes suivantes :

Getter pour tous les champs de classe.
Setter pour tous les champs non déclarés comme final.
Méthodes equals(), hashCode(), et toString() basées sur les champs de la classe.
Un constructeur sans argument

L'annotation @Builder de Lombok est utilisée pour générer automatiquement un constructeur de type builder pour une classe. Le pattern du constructeur de type builder est un moyen de créer des objets en utilisant une syntaxe fluide et lisible.
Lorsque vous utilisez l'annotation @Builder sur une classe ou sur un constructeur, Lombok génère automatiquement une classe de constructeur de type builder associée à votre classe, ce qui permet de créer des instances de cette classe de manière plus expressive.

Exemple :

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Personne {
private String nom;
private int age;
private String adresse;
}

// Utilisation du constructeur de type builder pour créer une instance
Personne personne = Personne.builder()
.nom("John Doe")
.age(30)
.adresse("123 Main Street")
.build();

@NoArgsConstructor: Cette annotation génère automatiquement un constructeur sans argument (constructeur par défaut) pour votre classe. Cela peut être utile lorsque vous avez besoin d'instancier des objets de cette classe sans spécifier explicitement les valeurs des champs. Par exemple, Spring Boot et d'autres frameworks utilisent souvent des classes sans constructeur argumenté pour créer des objets à partir de données reçues ou stockées.

@AllArgsConstructor: Cette annotation génère automatiquement un constructeur avec tous les arguments pour votre classe, ce qui signifie que vous n'avez pas besoin d'écrire ce constructeur manuellement. Cela peut être particulièrement utile lorsque vous avez une classe avec de nombreuses propriétés et que vous souhaitez éviter d'écrire un constructeur à longue liste d'arguments.

Si j'utilise NoArgsConstructor + AllArsConstructor sur le mêmem modèle j'aurai un constructeur avec et sans argument.

@Override est utilisé pour indiquer au compilateur que la méthode qui suit est une subsitution d'une méthode définie dans une classe parente ou une interface.
