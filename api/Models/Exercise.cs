using System.ComponentModel.DataAnnotations.Schema;

namespace api.Models;


[Table("Exercises")]
public class Exercise
{
    public int ExerciseId { get; set; }
    public string Name { get; set; } = "";
    public string Category { get; set; } = "";
    public string Description { get; set; } = "";
    public int Difficulty { get; set; }
    
    // Maximum of 18 digits in total and a maximum of 2 digits after the decimal point
    [Column(TypeName = "decimal(18, 2)")] 
    public decimal Popularity { get; set; }

    
}