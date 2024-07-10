using System.Globalization;
using Lab14.Domain;

namespace Lab14.Repository;

public class RepoAchizitii(string file, RepoFacturi fileRepository) : IFileRepository<string, Achizitie> {
    private readonly List<Achizitie> _achizitii = new();
    private RepoFacturi _repoFacturi = fileRepository;

    // public Achizitie Save(Achizitie entity){
    //     _achizitii.Add(entity);
    //     return entity;
    // }

    public IEnumerable<Achizitie> FindAll() {
        return _achizitii;
    }

    public string file { get; set; } = file;

    public void SaveToFile() {
        using StreamWriter toFile = new StreamWriter(file);
        foreach (var achizitie in _achizitii)
        {
            toFile.WriteLine($"{achizitie.ID},{achizitie.produs}," +
                             $"{achizitie.cantitate},{achizitie.pretProdus.ToString("0.00", CultureInfo.InvariantCulture)}," +
                             $"{achizitie.factura.ID}");
        }
    }

    public void GetFromFile() {
        try {
            using StreamReader fromFile = new StreamReader(file);
            while (!fromFile.EndOfStream) {
                var line = fromFile.ReadLine();
                if (line == null) continue;
                var args = line
                    .Split(',')
                    .ToList();

                var facturi = _repoFacturi
                    .FindAll()
                    .Where(x => x.ID.Equals(args[4]))
                    .ToList();
                
                if(facturi.Count.Equals(0)) continue;

                Achizitie achizitie = new Achizitie(
                    args[1],
                    int.Parse(args[2]),
                    double.Parse(args[3].Replace('.', ',')),
                    facturi[0]
                )
                {
                    ID = args[0],
                };
                    
                facturi[0].achizitii.Add(achizitie);
                
                _achizitii.Add(achizitie);
            }
        }
        catch (IOException e) {
            Console.WriteLine(e.Message);
        }
        catch (Exception e) {
            Console.WriteLine(e.Message);
        }
    }
}