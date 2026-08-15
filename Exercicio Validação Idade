package ExercicioPoo;
import java.util.Scanner;
public class ValidacaoIdade {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Digite sua idade:");
            int idade = scanner.nextInt();

            if (idade < 0 || idade > 150) {
                throw new IdadeInvalida("Idade inválida!");
            }
            System.out.println("Idade válida: " + idade);

        } catch (IdadeInvalida e) {
            System.out.println("Erro: " + e.getMessage());
        }

        scanner.close();
    }
}
