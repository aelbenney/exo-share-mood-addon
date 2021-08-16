package org.exoplatform.addons.sharemood.rest;

import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.exoplatform.addons.sharemood.Utils.EntityBuilder;
import org.exoplatform.addons.sharemood.entity.MoodEntity;
import org.exoplatform.addons.sharemood.entity.rest.Mood;
import org.exoplatform.addons.sharemood.services.MoodDTO;
import org.exoplatform.addons.sharemood.services.MoodService;
import org.exoplatform.common.http.HTTPStatus;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.exoplatform.services.security.ConversationState;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.List;

@Path("/shareMood")
@Api(value = "/shareMood", description = "Manages shared mood of current user") // NOSONAR
public class ShareMoodRestService implements ResourceContainer {
  private static final Log LOG = ExoLogger.getExoLogger(ShareMoodRestService.class);

  private MoodService moodService;

  public ShareMoodRestService(MoodService moodService) {
    this.moodService = moodService;
  }

  @POST
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed("users")
  @ApiResponses(
    value = { @ApiResponse(code = HTTPStatus.NO_CONTENT, message = "Request fulfilled"),
      @ApiResponse(code = HTTPStatus.BAD_REQUEST, message = "Invalid query input"),
      @ApiResponse(code = HTTPStatus.UNAUTHORIZED, message = "Unauthorized operation"),
      @ApiResponse(code = HTTPStatus.INTERNAL_ERROR, message = "Internal server error"),
    }
  )
  public Response saveMood(@QueryParam(value = "mood") String mood) {
    try {
      if (StringUtils.isBlank(mood)) {
        return Response.status(Response.Status.BAD_REQUEST).entity("Shared mood should not be empty").build();
      }
      String loggedInUser = ConversationState.getCurrent().getIdentity().getUserId();
      if (loggedInUser == null) {
        return Response.status(Response.Status.FORBIDDEN).build();
      }
      MoodDTO moodDTO = moodService.saveMood(MoodEntity.Mood.valueOf(mood.toUpperCase()), loggedInUser);
      Mood moodSaved = EntityBuilder.convertToMood(moodDTO);
      return Response.ok(moodSaved).build();
    } catch (Exception e) {
      LOG.error("Error while saving shared mood for current user", e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed("users")
  public Response updateMood(@QueryParam("mood") String mood) {
    try {
      String loggedInUser = ConversationState.getCurrent().getIdentity().getUserId();
      MoodDTO moodDTO = moodService.updateMood(MoodEntity.Mood.valueOf(mood.toUpperCase()), loggedInUser);
      return Response.ok("Mood Updated to " + moodDTO.getMood() + " for user " + moodDTO.getUsername(),
                         MediaType.APPLICATION_JSON_TYPE)
                     .build();
    } catch (Exception e) {
      LOG.error("Error while calling /rest/sharemood/update Rest service", e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GET
  @Path("load")
  @Produces(MediaType.APPLICATION_JSON)
  public Response loadMoods(@QueryParam("username") String username, @QueryParam("since") String since) {
    try {
      Calendar sinceDate = Calendar.getInstance();
      sinceDate.set(Calendar.WEEK_OF_YEAR, sinceDate.getWeekYear() - 1);
      if (since != null) {
        switch (since.toLowerCase()) {
        case "lastweek":
          sinceDate.set(Calendar.WEEK_OF_MONTH, sinceDate.get(Calendar.WEEK_OF_MONTH) - 1);
          LOG.info("Time selected {}", sinceDate.getTime());
          break;
        case "lastmonth":
          sinceDate.set(Calendar.MONTH, sinceDate.get(Calendar.MONTH) - 1);
          LOG.info("Time selected {}", sinceDate.getTime());
          break;
        case "lastquarter":
          sinceDate.set(Calendar.MONTH, sinceDate.get(Calendar.MONTH) - 3);
          LOG.info("Time selected {}", sinceDate.getTime());
          break;
        case "lastsimester":
          sinceDate.set(Calendar.MONTH, sinceDate.get(Calendar.MONTH) - 6);
          LOG.info("Time selected {}", sinceDate.getTime());
          break;
        }
      }
      JSONArray jsonArray = new JSONArray();
      for (MoodEntity.Mood mood : MoodEntity.Mood.values()) {
        List<MoodDTO> moods = moodService.loadMoods(username, mood, sinceDate);
        JSONObject object = new JSONObject();
        object.put("mood", mood);
        object.put("count", moods.size());
        object.put("period", since);
        jsonArray.put(object);
      }

      return Response.ok(jsonArray.toString(), MediaType.APPLICATION_JSON).build();
    } catch (Exception e) {
      LOG.error("Error while calling /rest/sharemood/load Rest service", e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GET
  @Path("loadToday")
  @Produces(MediaType.APPLICATION_JSON)
  public Response loadMoodOfToday(@QueryParam("username") String username) {
    try {
      MoodDTO moodOfToday = moodService.find(username, Calendar.getInstance());
      Mood moodSaved = EntityBuilder.convertToMood(moodOfToday);
      return Response.ok(moodSaved).build();
    } catch (Exception e) {
      LOG.error("Could not get the saved mood of user {} ", username, e);
      return Response.serverError().build();
    }

  }
}
