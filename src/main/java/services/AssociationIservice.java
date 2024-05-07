package services;

import com.example.pidev.entities.Association;

import java.util.List;

public interface AssociationIservice {

    public List<Association> getAll();

    public boolean add(Association association);

    public boolean edit(Association association);

    public boolean delete(int id);
}
