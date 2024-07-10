using Lab14.Domain;

namespace Lab14.Repository;

public class RepoFacturi(string file, RepoDocumente fileRepository) : IFileRepository<string, Factura> {
    private readonly List<Factura> _facturi = new();
    private RepoDocumente _repoDocumente = fileRepository;

    // public Factura Save(Factura entity)
    // {
    //     _facturi.Add(entity);
    //     return entity;
    // }

    public IEnumerable<Factura> FindAll() {
        return _facturi;
    }

    public string file { get; set; } = file;

    public void SaveToFile() {
        using StreamWriter toFile = new StreamWriter(file);
        foreach (var factura in _facturi) {
            toFile.WriteLine($"{factura.ID},{factura.DataScadenta:dd.MM.yyyy}," +
                             $"{factura.Categorie.GetHashCode()}");
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

                var document = _repoDocumente
                    .FindAll()
                    .Where(x => x.ID.Equals(args[0]))
                    .ToList();
                if(document.Count.Equals(0)) continue;
                
                _facturi.Add(new Factura(
                    document[0],
                    DateTime.Parse(args[1]),
                    Enum.Parse<Factura.Enum>(args[2])
                    ) 
                {
                    ID = args[0]
                });
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