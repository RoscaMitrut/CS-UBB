using Lab14.Repository;
using Lab14.Service;
using Lab14.UI;

public class Start {
    public static void Main() {
        var documentsFileRepository = new RepoDocumente(
            "..\\..\\..\\Files\\documente.txt");
        
        var facturiFileRepository = new RepoFacturi(
            "..\\..\\..\\Files\\facturi.txt", 
            documentsFileRepository);
        
        var achizitiiFileRepository = new RepoAchizitii(
            "..\\..\\..\\Files\\achizitii.txt", 
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