package qetaa.service.vehicles.restful;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import qetaa.service.vehicles.dao.DAO;
import qetaa.service.vehicles.filters.ValidApp;
import qetaa.service.vehicles.model.Make;
import qetaa.service.vehicles.model.Model;
import qetaa.service.vehicles.model.ModelYear;
import qetaa.service.vehicles.model.contract.PublicMake;
import qetaa.service.vehicles.model.contract.PublicModel;
import qetaa.service.vehicles.model.contract.PublicModelYear;
import qetaa.service.vehicles.model.contract.PublicModelYearContained;

@Path("/api/v1/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApiV1 {

	@EJB
	private DAO dao;

	@ValidApp
	@POST
	@Path("model-year-from-ids")
	public Response getVehiclesFromIds(List<Integer> modelYearIds) {
		try {
			String sql = "select * from veh_year where id in (0";
			for (Integer myId : modelYearIds) {
				sql += "," + myId;
			}
			sql += ")";
			List<ModelYear> mys = dao.getNative(ModelYear.class, sql);
			List<PublicModelYearContained> publicMys = new ArrayList<>();
			for (ModelYear modelYear : mys) {
				PublicModelYearContained pm = new PublicModelYearContained(modelYear);
				publicMys.add(pm);
			}			
			return Response.status(200).entity(publicMys).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@ValidApp
	@GET
	@Path("makes")
	public Response getAllVehicles() {
		try {
			List<Make> makes = dao.getConditionOrderBy(Make.class, "status", 'A', "name");
			List<PublicMake> pmakes = new ArrayList<>();
			for (Make make : makes) {
				PublicMake pm = new PublicMake(make, getPublicModels(make.getId()));
				pmakes.add(pm);
			}
			return Response.status(200).entity(pmakes).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	private List<PublicModel> getPublicModels(int makeId) {
		String jpql = "select b from Model b where b.make.id = :value0 and b.status = :value1 order by b.name";
		List<Model> models = dao.getJPQLParams(Model.class, jpql, makeId, 'A');
		List<PublicModel> pmodels = new ArrayList<>();
		for (Model model : models) {
			PublicModel pm = new PublicModel(model, getPublicModelYears(model.getId()));
			pmodels.add(pm);

		}
		return pmodels;
	}

	private List<PublicModelYear> getPublicModelYears(int modelId) {
		String jpql = "select b from ModelYear b where b.model.id = :value0 and b.status = :value1 order by b.year";
		List<ModelYear> modelYears = dao.getJPQLParams(ModelYear.class, jpql, modelId, 'A');
		List<PublicModelYear> pmodelYears = new ArrayList<>();
		for (ModelYear modelYear : modelYears) {
			PublicModelYear pm = new PublicModelYear(modelYear);
			pmodelYears.add(pm);
		}
		return pmodelYears;
	}

}
