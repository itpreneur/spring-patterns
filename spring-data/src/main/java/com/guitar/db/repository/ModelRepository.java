package com.guitar.db.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.guitar.db.model.Model;

@Repository
public class ModelRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ModelJpaRepository modelJpaRepository;

	/**
	 * Create
	 */
	public Model create(Model mod) {
		return modelJpaRepository.saveAndFlush(mod);
	}

	/**
	 * Update
	 */
	public Model update(Model mod) {
		return modelJpaRepository.saveAndFlush(mod);
	}

	/**
	 * Delete
	 */
	public void delete(Model mod) {
		modelJpaRepository.delete(mod);
	}

	/**
	 * Find
	 */
	public Model find(Long id) {
		return modelJpaRepository.findOne(id);
	}

	/**
	 * Custom finder
	 */
	public List<Model> getModelsInPriceRange(BigDecimal lowest, BigDecimal highest) {

        List<Model> mods = modelJpaRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(lowest,highest);
		return mods;
	}

	/**
	 * Custom finder
	 */
	public List<Model> getModelsByPriceRangeAndWoodType(BigDecimal lowest, BigDecimal highest, String wood) {
		return modelJpaRepository.queryByPriceRangeAndWoodType(lowest, highest, "%" + wood + "%");
	}

    /**
     * Custom pagable finder
     */
    public Page<Model> getModelsByPriceRangeAndWoodTypePage(BigDecimal lowest, BigDecimal highest, String wood, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        Pageable page = new PageRequest(0, pageSize, sort);
        return modelJpaRepository.queryByPriceRangeAndWoodTypePageable(lowest, highest, "%" + wood + "%", page);
    }

	/**
	 * NamedQuery finder
	 */
	public List<Model> getModelsByType(String modelType) {
		return modelJpaRepository.namedFindAllModelsByType(modelType);
	}

	/**
	 * Count
	 */
	public Long getModelCount() {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(Model.class)));
		return entityManager.createQuery(cq).getSingleResult();
	}
}
