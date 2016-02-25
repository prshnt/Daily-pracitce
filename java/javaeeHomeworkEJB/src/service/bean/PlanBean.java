package service.bean;

import entity.Branch;
import entity.Dessert;
import entity.Plan;
import entity.PlanDetail;
import service.PlanService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

/**
 * Created by zzt on 2/23/16.
 * <p>
 * Usage:
 */
@Stateless(name = "PlanEJB")
public class PlanBean implements PlanService {

    @PersistenceContext
    EntityManager em;

    public PlanBean() {
    }

    @Override
    public void addPlan(int bid, String planDate) {
        em.persist(new Plan(em.find(Branch.class, bid), planDate));
    }

    @Override
    public void deletePlan(int planId) {
        em.remove(em.find(Plan.class, planId));
    }

    @Override
    public void updatePlan(Plan plan) {
        em.merge(plan);
    }

    @Override
    public ArrayList<Plan> newPlan() {
        return (ArrayList<Plan>) em.createNamedQuery(Plan.NEW_PLAN, Plan.class).getResultList();
    }

    @Override
    public ArrayList<Plan> branchPlan(int bid) {
        return (ArrayList<Plan>) em.createNamedQuery(Plan.BRANCH_PLAN, Plan.class)
                .setParameter(1, bid)
                .getResultList();
    }

    @Override
    public void addPlanDetail(int planId, int num, int did) {
        Plan plan = em.find(Plan.class, planId);
        Dessert dessert = em.find(Dessert.class, did);
        em.persist(new PlanDetail(plan, dessert, num));
    }
}
