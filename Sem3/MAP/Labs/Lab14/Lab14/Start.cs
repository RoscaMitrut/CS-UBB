using Lab14.Repository;
using Lab14.Service;
using Lab14.UI;

public class Start {
    public static void Main() {
        var documentsFileRepository = new RepoDocumente(
            "C:\\Users\\RoscaMitrut\\Desktop\\SEM_III\\MAP\\Lab14\\Lab14\\Files\\documente.txt");
        
        var facturiFileRepository = new RepoFacturi(
            "C:\\Users\\RoscaMitrut\\Desktop\\SEM_III\\MAP\\Lab14\\Lab14\\Files\\facturi.txt", 
            documentsFileRepository);
        
        var achizitiiFileRepository = new RepoAchizitii(
            "C:\\Users\\RoscaMitrut\\Desktop\\SEM_III\\MAP\\Lab14\\Lab14\\Files\\achizitii.txt", 
            facturiFileRepository);

        documentsFileRepository.GetFromFile();
        facturiFileRepository.GetFromFile();
        achizitiiFileRepository.GetFromFile();

        Service service = new Service(
            documentsFileRepository, 
            facturiFileRepository, 
            achizitiiFileRepository);
        Ui ui = new Ui(service);
        ui.Main();
    }   
}