using Lab14.Domain;

namespace Lab14.Repository;

public class RepoDocumente(string file) : IFileRepository<string, Document> {
    private readonly List<Document> _documents = new();

    // public Document Save(Document entity)
    // {
    //     _documents.Add(entity);
    //     return entity;
    // }

    public IEnumerable<Document> FindAll()
    {
        return _documents;
    }

    public string file { get; set; } = file;

    public void SaveToFile()
    {
        using StreamWriter toFile = new StreamWriter(file);
        foreach (var document in _documents)
        {
            toFile.WriteLine($"{document.ID},{document.Nume},{document.DataEmitere:dd.MM.yyyy}");
        }
    }

    public void GetFromFile()
    {
        try
        {
            using StreamReader fromFile = new StreamReader(file);
            while (!fromFile.EndOfStream) {
                var line = fromFile.ReadLine();
                if (line == null) continue;
                
                var args = line.Split(',').ToList();
                DateTime date = DateTime.Parse(args[2]);
                //DateTime date = DateTime.ParseExact(args[2], "dd/MM/yyyy", null);
                Console.WriteLine(date);
                _documents.Add(new Document(args[0], args[1], date));
            }
        }
        catch (IOException e)
        {
            Console.WriteLine(e.Message);
        }

    }
}

