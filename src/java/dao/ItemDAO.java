/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Item;
import java.util.Collection;
import javax.persistence.Query;

/**
 *
 * @author Pedro Henrique
 */
public class ItemDAO extends DAO {
    
    public ItemDAO() {
        super(Item.class);
    }
    
    public Collection<Item> findAllItens() {
        Query query = manager.createQuery("Select e From Item e");
        return (Collection<Item>) query.getResultList();
    }
}
