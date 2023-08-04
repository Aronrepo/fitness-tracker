// WARNING:
// - this needs imports and export
// - implement countCalories

const countCalories = (foodItems) => { /* ???? */ };

const useCaloryCounted = (initialFoodItems) => {
  const [calories, setCalories] = useState(() => countCalories(initialFoodItems));
  const [foodItems, setFoodItems] = useState(initialFoodItems);

  useEffect(() => {
    setCalories(countCalories(foodItems));
  }, [foodItems]);

  return [foodItems, setFoodItems, calories];
}
