package com.example.pidev.services;

import com.example.pidev.entities.Sponsor;

import java.util.List;

public interface SponsorIservice {

    public List<Sponsor> getAll();

    public boolean add(Sponsor sponsor);

    public boolean edit(Sponsor sponsor);

    public boolean delete(int id);
}
