package qetaa.service.vehicles.restful;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import qetaa.service.vehicles.dao.DAO;
import qetaa.service.vehicles.model.contract.MakeContract;
import qetaa.service.vehicles.model.contract.ModelContract;
import qetaa.service.vehicles.model.contract.ModelYearContract;

@Path("/external/")
public class ExternalService {

	@EJB
	private DAO dao;
		
//	@ValidApp
	@GET
	@Path("makes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllVehicles() {
		try {
			List<MakeContract> makes = dao.getOrderBy(MakeContract.class, "name");
			for (MakeContract make : makes) {
				List<ModelContract> models = dao.getConditionOrderBy(ModelContract.class, "makeId", make.getId(), "name");
				make.setModels(models);
				for (ModelContract model : models) {
					List<ModelYearContract> modelYears = dao.getConditionOrderBy(ModelYearContract.class, "modelId", model.getId(), "year");
					model.setModelYears(modelYears);
				}
			}
			return Response.status(200).entity(makes).build();
		} catch (Exception ex) {
			return Response.status(500).build();
		}
	}
}
