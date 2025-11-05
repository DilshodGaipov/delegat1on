package com.ddcorp.delegat1on.rest;
import javax.ws.rs.*; import javax.ws.rs.core.*; import javax.inject.*; import java.util.*; import com.ddcorp.delegat1on.api.*; import com.google.gson.Gson;
@Path("/delegat1on") @Consumes(MediaType.APPLICATION_JSON) @Produces(MediaType.APPLICATION_JSON)
public class DelegationResource {
  @Inject @Named("delegationService") private DelegationService service;
  @Inject @Named("delegationConfig") private ConfigService cfg;
  private final Gson gson=new Gson();

  @GET @Path("/ping") public Response ping(){ return Response.ok("{\"status\":\"ok\"}").build(); }

  // Records
  @GET @Path("/records")
  public Response list(@QueryParam("delegatorKey") String delegatorKey, @QueryParam("delegateKey") String delegateKey, @QueryParam("active") @DefaultValue("0") int active){
    List<DelegationRecord> list = service.all(); Date now = new Date();
    if (delegatorKey!=null) list.removeIf(r->!delegatorKey.equals(r.delegatorKey));
    if (delegateKey!=null)  list.removeIf(r->!delegateKey.equals(r.delegateKey));
    if (active==1) list.removeIf(r->!r.isActiveAt(now));
    return Response.ok(gson.toJson(list)).build();
  }
  @POST @Path("/records") public Response create(String body){ DelegationRecord rec=gson.fromJson(body,DelegationRecord.class); if(rec==null) return Response.status(400).entity("{\"error\":\"bad payload\"}").build(); return Response.ok(gson.toJson(service.create(rec))).build(); }
  @PUT @Path("/records/{id}") public Response update(@PathParam("id") long id, String body){ DelegationRecord rec=gson.fromJson(body,DelegationRecord.class); if(rec==null) return Response.status(400).entity("{\"error\":\"bad payload\"}").build(); rec.id=id; DelegationRecord u=service.update(rec); if(u==null) return Response.status(404).build(); return Response.ok(gson.toJson(u)).build(); }
  @DELETE @Path("/records/{id}") public Response delete(@PathParam("id") long id){ return service.delete(id)?Response.noContent().build():Response.status(404).build(); }

  // Categories
  @GET @Path("/categories") public Response categories(){ return Response.ok(gson.toJson(cfg.getCategories())).build(); }
  @POST @Path("/categories") public Response addCategory(String body){ Map m=gson.fromJson(body, Map.class); Object n=m.get("name"); if(n==null) return Response.status(400).build(); cfg.addCategory(String.valueOf(n)); return Response.status(201).build(); }
  @DELETE @Path("/categories") public Response delCategory(@QueryParam("name") String name){ if(name==null) return Response.status(400).build(); cfg.removeCategory(name); return Response.noContent().build(); }

  // Permissions
  @GET @Path("/perms") public Response perms(){ return Response.ok(gson.toJson(cfg.getPermissions())).build(); }
  @PUT @Path("/perms") public Response setPerms(String body){ Map<String,Boolean> p=gson.fromJson(body, Map.class); cfg.setPermissions(p); return Response.noContent().build(); }
}
