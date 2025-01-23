using System.ComponentModel.DataAnnotations.Schema;

namespace api.Models;

[Table("Workouts")]
public class Workout
{
    public string AppUserId { get; set; }
    public AppUser AppUser { get; set; }
    public Exercise[] Exercises { get; set; } = [];
}