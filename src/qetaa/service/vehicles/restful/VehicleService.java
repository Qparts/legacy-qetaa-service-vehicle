package qetaa.service.vehicles.restful;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import qetaa.service.vehicles.dao.DAO;
import qetaa.service.vehicles.filters.Secured;
import qetaa.service.vehicles.filters.SecuredCustomer;
import qetaa.service.vehicles.filters.SecuredUser;
import qetaa.service.vehicles.filters.SecuredVendor;
import qetaa.service.vehicles.filters.ValidApp;
import qetaa.service.vehicles.model.Make;
import qetaa.service.vehicles.model.Model;
import qetaa.service.vehicles.model.ModelYear;

@Path("/")
public class VehicleService {
	@EJB
	private DAO dao;

	@Secured
	@SecuredCustomer
	@SecuredVendor
	@GET
	public void test() {

	}

	@GET
	@Path("/tet")
	public String test1() {
		return "hala walla";
	}

	// idempotent
	@SecuredUser
	@POST
	@Path("make")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createVehicle(Make make) {
		try {
			List<Make> makes = dao.getCondition(Make.class, "name", make.getName());
			if (makes.isEmpty()) {
				dao.persist(make);
				return Response.status(200).build();
			} else {
				return Response.status(409).build();
			}

		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	// idempotent
	@SecuredUser
	@POST
	@Path("model")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createModel(Model model) {
		try {
			List<Model> types = dao.getTwoConditions(Model.class, "name", "make", model.getName(), model.getMake());
			if (types.isEmpty()) {
				dao.persist(model);
				return Response.status(200).build();
			} else {
				return Response.status(409).build();
			}
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@Secured
	@GET
	@Path("all-model-years")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllModelYears() {
		try {
			List<ModelYear> modelYears = dao.get(ModelYear.class);
			return Response.status(200).entity(modelYears).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}

	}

	@Secured
	@GET
	@Path("model-year/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModelYearFromId(@PathParam(value = "param") int modelYearId) {
		try {
			ModelYear modelYear = dao.find(ModelYear.class, modelYearId);
			if (modelYear != null) {
				return Response.status(200).entity(modelYear).build();
			} else {
				return Response.status(404).build();
			}
		} catch (Exception ex) {
			return Response.status(500).build();
		}

	}

	// idempotent
	@SecuredUser
	@POST
	@Path("model-year")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createYear(ModelYear modelYear) {
		try {
			List<ModelYear> years = dao.getTwoConditions(ModelYear.class, "year", "model", modelYear.getYear(),
					modelYear.getModel());
			if (years.isEmpty()) {
				modelYear.setCreated(new Date());
				dao.persist(modelYear);
				return Response.status(200).build();
			} else {
				return Response.status(409).build();
			}

		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@PUT
	@Path("make")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMake(Make make) {
		try {
			dao.update(make);
			return Response.status(200).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@PUT
	@Path("model")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateModel(Model model) {
		try {
			dao.update(model);
			return Response.status(200).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@PUT
	@Path("model-year")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateModelYear(ModelYear modelYear) {
		try {
			dao.update(modelYear);
			return Response.status(200).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@ValidApp
	@GET
	@Path("all-active-makes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllActiveVehicles() {
		try {
			List<Make> makes = dao.getCondition(Make.class, "status", 'A');
			for (Make make : makes) {
				List<Model> models = dao.getTwoConditions(Model.class, "make", "status", make, 'A');
				make.setModels(models);
				for (Model model : models) {
					List<ModelYear> modelYears = dao.getTwoConditionsOrdered(ModelYear.class, "model", "status", model,
							'A', "year", "ASC");
					model.setModelYears(modelYears);
				}
			}
			return Response.status(200).entity(makes).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@POST
	@Path("all-other-active-makes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOtherMakes(List<Integer> selectedMakes) {
		try {
			String params = "0";
			for (Integer i : selectedMakes) {
				params = params + "," + i;
			}
			String sql = "select * from veh_make where id not in (" + params + ") and status = 'A'";
			List<Make> makes = dao.getNative(Make.class, sql);
			return Response.status(200).entity(makes).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@Secured
	@GET
	@Path("/make/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMakeFromId(@PathParam(value = "param") int makeId) {
		try {
			Make make = dao.find(Make.class, makeId);
			return Response.status(200).entity(make).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}

	@SecuredUser
	@GET
	@Path("all-makes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllVehicles() {
		try {
			List<Make> makes = dao.getOrderBy(Make.class, "name");
			for (Make make : makes) {
				List<Model> models = dao.getConditionOrderBy(Model.class, "make", make, "name");
				make.setModels(models);
				for (Model model : models) {
					List<ModelYear> modelYears = dao.getConditionOrderBy(ModelYear.class, "model", model, "year");
					model.setModelYears(modelYears);
				}
			}
			return Response.status(200).entity(makes).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}
}
