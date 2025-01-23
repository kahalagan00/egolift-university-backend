using api.Data;
using api.Models;
using Microsoft.AspNetCore.Mvc;

namespace api.Controllers;

[ApiController]
[Route("api/exercise")]
public class ExerciseController : ControllerBase
{
   private readonly ApplicationDBContext _context;
    public ExerciseController(ApplicationDBContext context)
    {
        Console.WriteLine("[FATDBG] New Exercise object created.");
        _context = context;
    }

    [HttpGet]
    public IActionResult GetAll()
    {
        return Ok(new { message = "Hello, World!" });
    }
    
    [HttpGet("{exerciseId}")]
    public Exercise GetExerciseById([FromRoute] int exerciseId)
    {
        return new Exercise
        {
            ExerciseId = exerciseId,
            Difficulty = 8,
            Name = "Pull Ups",
            Popularity = (decimal)55.5,
            Category = "Pulling",
            Description = "Pulling your body upwards vertically in a bar."
        };
    }
    
    
}