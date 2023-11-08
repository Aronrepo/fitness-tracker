import React, { useState, useEffect } from "react";
import "./CalorieForm.css";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Notification from "../../components/Notification/Notification";
import { Box } from "@mui/material";
import Cookies from "js-cookie";
import DailyBarchart from "../../components/DailyBarchart/DailyBarchart";
import { Add } from "@mui/icons-material";
import InputAdornment from "@mui/material/InputAdornment";
import IconButton from "@mui/material/IconButton";
import SearchIcon from "@mui/icons-material/Search";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import { PieChart } from "@mui/x-charts/PieChart";

const CalorieForm = () => {
  const [grams, setGrams] = useState(0);
  const [foodType, setFoodType] = useState();
  const [open, setOpen] = useState(false);
  const [flexDirection, setFlexDirection] = useState('row');
  const [searchedFood, setSearchedFood] = useState("");
  const [isSearched, setIsSearched] = useState(true);
  const [foundFoodTypes, setFoundFoodTypes] = useState([]);
  const [dailyCalorieBalanceInfos, setDailyCalorieBalanceInfos] = useState([
    {
      requiedCalorie: 0,
      dailyCalorieConsumption: 0,
      dailyActivityCalorie: 0
    },
  ]);
  const [duration, setDuration] = useState("daily");
  const jwtToken = Cookies.get("jwtToken");
  const [snackbarError, setSnackbarError] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);

  const handleCaloriesChange = (event) => {
    setGrams(event.target.value);
  };

  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }

    setOpen(false);
  };

  useEffect(() => {
    fetchDailyCalories().then((listedMeals) => {
      setDailyCalorieBalanceInfos(listedMeals);
    });
  }, []);

  const fetchDailyCalories = () => {
    return fetch(`/analyze/?duration=${duration}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error("Could not fetch the data for that resource");
        }
        return res.json();
      })
      .then((data) => {
        return data;
      })
      .catch((error) => {
        console.log(error.message);
      });
  };

  const showDailyBarChart = () => {
    if (window.innerWidth <= 960) {
      setFlexDirection('column');
    } else {
      setFlexDirection('row');
    }
  };

  const fetchFoodType = () => {
    return fetch(`/foodtype/search?&foodType=${searchedFood}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${jwtToken}`,
      },
    })
    .then((res) => {
      if (!res.ok) {
        throw new Error("Could not fetch the data for that resource");
      }
      return res.json();
    })
    .then((data) => {
      return data;
    })
    .catch((error) => {
      console.log(error.message);
    });
  };

  const handleSearchClick = () => {
    fetchFoodType().then((listedFoodTypes) => {
              setIsSearched(true);
        setFoundFoodTypes(listedFoodTypes);
        console.log(listedFoodTypes);
          });
  };

  const handleSelectFoodType = (selectedFoodType) => {
    setFoodType(selectedFoodType);
    setIsSearched(false);
  };

  window.addEventListener("resize", showDailyBarChart);

  const handleSubmit = async (event) => {
    setSuccessMessage(null);
    setSnackbarError(null);
    event.preventDefault();
    if (grams <= 0) {
      setOpen(true);
      setSnackbarError("Amount must be greater than zero");
      return;
    }
    try {
      const response = await fetch("/calories/", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwtToken}`,
        },
        body: JSON.stringify({
          apiId: foodType.apiId,
          consumption: grams,
        }),
      });

      if (!response.ok) {
        setOpen(true);
        setSnackbarError("Failed to post calories.");
        throw new Error("Failed to post calories.");
      }
      setOpen(true);
      setSuccessMessage("Posted meal")
      fetchDailyCalories().then((listedMeals) => {
        setDailyCalorieBalanceInfos(listedMeals);
      });
      const data = await response.json();
      console.log(data);
    } catch (error) {
      console.error("Error posting calories:", error);
    }
  };

  return (
    <>
      <Box flex={5} sx= {{
         display: 'flex',
         flexDirection: {flexDirection},
         justifyContent: 'space-between',
         p: 1,
         m: 1,
         bgcolor: 'background.paper',
         borderRadius: 1,
      }} >
      {isSearched ? (
          <Box>
            <TextField
              
            sx={{
              width: "100%",
            }}
              id="outlined-search"
              label="Search food"
              type="search"
              value={searchedFood}
              onChange={(event) => setSearchedFood(event.target.value)}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton onClick={handleSearchClick}>
                      <SearchIcon />
                    </IconButton>
                  </InputAdornment>
                ),
              }}
            />
            {foundFoodTypes !== undefined ? (
            <Table
            sx={{width: "100%"}}>
              {foundFoodTypes.length !== 0 ? (
                <TableHead>
                  <TableRow>
                    <TableCell>Food Type (100g serving)</TableCell>
                    <TableCell>Calorie</TableCell>
                    <TableCell>Carbohydrate</TableCell>
                    <TableCell>Fat</TableCell>
                    <TableCell>Protein</TableCell>
                  </TableRow>
                </TableHead>
              ) : null}
              <TableBody>
                {foundFoodTypes.map((foodtype) => (
                  <TableRow
                    sx={{ cursor: "pointer" }}
                    key={foodtype.apiId}
                    onClick={() => handleSelectFoodType(foodtype)}
                  >
                    <TableCell>{foodtype.name}</TableCell>
                    <TableCell>{foodtype.calorie}</TableCell>
                    <TableCell>{foodtype.carbohydrate}</TableCell>
                    <TableCell>{foodtype.fat}</TableCell>
                    <TableCell>{foodtype.protein}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>

            ) : (
              <h6>No such food found</h6>
            )}
          </Box>
        ) : (
          <>
            <TextField
              id="outlined-search"
              label="Search food"
              type="search"
              fullWidth
              value={searchedFood}
              onChange={(event) => setSearchedFood(event.target.value)}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton onClick={handleSearchClick}>
                      <SearchIcon />
                    </IconButton>
                  </InputAdornment>
                ),
              }}
            />
            <form className="calorie-form" onSubmit={handleSubmit}>
              <label htmlFor="grams">Amount:</label>
              <TextField
                type="number"
                style={{ width: "345px" }}
                id="outlined-basic"
                label="Amount"
                variant="outlined"
                onChange={handleCaloriesChange}
                value={grams}
                sx={{ mb: 2 }}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">g</InputAdornment>
                  ),
                }}
              />
              <Card sx={{ maxWidth: 345, mb: 2 }}>
                <CardMedia
                  sx={{ height: 140 }}
                  image="https://media.istockphoto.com/id/1191979640/hu/fot%C3%B3/friss-z%C3%B6lds%C3%A9gek-%C3%A9s-gy%C3%BCm%C3%B6lcs%C3%B6k-sz%C3%ADnes-gy%C3%BCm%C3%B6lcs%C3%B6k-%C3%A9s-z%C3%B6lds%C3%A9gek-tiszta-%C3%A9tkez%C3%A9s-z%C3%B6lds%C3%A9gek-%C3%A9s.jpg?s=1024x1024&w=is&k=20&c=1qbShF6KITFNK-3FTa0t2GgNaSE3za6SLKjhiG9Qrqs="
                  title="food"
                />
                <CardContent>
                  <Typography gutterBottom variant="h5" component="div">
                    {foodType.name}
                  </Typography>
                  <PieChart
                    series={[
                      {
                        data: [
                          { id: 0, value: foodType.carbohydrate, label: `Carbs:${foodType.carbohydrate}` },
                          { id: 1, value: foodType.protein, label: `Protein:${foodType.protein}` },
                          { id: 2, value: foodType.fat, label: `Fat:${foodType.fat}` },
                        ],
                        outerRadius: 70,
                        cx: 80,
                      },
                    ]}
                    width={325}
                    height={160}
                    slotProps={{
                      legend: {
                        position: {
                          vertical: 'middle',
                          horizontal: 'right',
                        },
                        itemMarkWidth: 20,
                        itemMarkHeight: 2,
                        markGap: 5,
                        itemGap: 10,
                      }
                    }}
                  />
                </CardContent>
              </Card>
              <Button
                variant="contained"
                type="submit"
                style={{  minWidth: '345px' }}
              >
                Post Calories
              </Button>
              <Notification
                snackbarOpen={open}
                onClose={handleClose}
                successMassage={successMessage}
                snackbarError={snackbarError}
              />
            </form>
          </>
        )}
        <Box>
        <DailyBarchart todayBalance={dailyCalorieBalanceInfos} />

        </Box>
      </Box>
    </>
  );
};

export default CalorieForm;
