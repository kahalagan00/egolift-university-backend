using api.Data;
using api.Models;
using Microsoft.AspNetCore.Mvc;

namespace api.Controllers;


[Route("api/workout")]
public class WorkoutController : ControllerBase
{
    private readonly ApplicationDBContext _context;

    public WorkoutController(ApplicationDBContext context)
    {
        _context = context;
    }
    
    [HttpGet]
    public Workout GetAll()
    {
        return new Workout
        {
            Exercises = [],
            AppUser = null,
            AppUserId = "sadad12231231"
        };
    }   
    

}