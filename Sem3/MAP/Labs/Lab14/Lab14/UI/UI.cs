using Lab14.Domain;
using Lab14.Service;

namespace Lab14.UI;

public class Ui(Service.Service serviceInput) {
    private void ListAll(){
        Console.WriteLine("Documents: ");

        serviceInput
            .FindDocumente()
            .ForEach(
                Console.WriteLine
            );

        Console.WriteLine("\nFacturi:");
        
        serviceInput
            .FindFacturi()
            .ForEach(
                Console.WriteLine
            );
        
        Console.WriteLine("\nAchizitii:");
        
        serviceInput
            .FindAchizitii()
            .ForEach(
                Console.WriteLine
            );
    }

    private bool ParseCommand(string? line) {
        switch (line) {
            case "0":
                ListAll();
                break;
            
            case "1":
                serviceInput.DocumenteEmise2023()
                    .ForEach(
                        delegate(Document document) {
                            Console.WriteLine($"{document.Nume} {document.DataEmitere:dd.MM.yyyy}");
                        }
                    );
                break;
            
            case "2":
                serviceInput.FacturiScadenteLunaCurenta()
                    .ForEach(
                        delegate(Factura factura) {
                            Console.WriteLine($"{factura.Nume} {factura.DataScadenta:dd.MM.yyyy}");
                        }
                    );
                break;
            
            case "3":
                serviceInput.FacturiCu3Achizitii()
                    .ForEach(
                        delegate(Factura factura) {
                            Console.WriteLine($"{factura.Nume} {Service.Service.NrProduse(factura)}");
                        }
                    );
                break;
            
            case "4":
                serviceInput.AchizitiiUtilities()
                    .ForEach(
                        delegate(Achizitie achizitie) {
                            Console.WriteLine($"{achizitie.produs} {achizitie.factura.Nume}");
                        }
                    );
                break;
            
            case "5":
                Console.WriteLine(serviceInput.CategoriaCuCheltuialaMaxima());
                break;
            
            default:
                return true;
        }

        return false;
    }

    public void Main() {
        while (true) {
            Console.WriteLine("Command: ");
            if (ParseCommand(Console.ReadLine()))
                return;
        }

    }

}