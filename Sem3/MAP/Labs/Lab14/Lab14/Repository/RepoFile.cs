using Lab14.Domain;

namespace Lab14.Repository;

public interface IFileRepository<ID, E> : IRepository<ID, E> where E : Entity<ID> {
    string file { set; get; }

    void SaveToFile();
    void GetFromFile();
}