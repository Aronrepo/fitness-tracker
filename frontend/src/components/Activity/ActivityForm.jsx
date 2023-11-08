import React, { useEffect, useState } from "react";
import Button from "@mui/material/Button";
import SearchIcon from "@mui/icons-material/Search";
import "./ActivityForm.css";

import Notification from "../Notification/Notification";
import {
  Box,
  Stack,
  Skeleton,
  TextField,
  InputAdornment,
  IconButton,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  CardMedia,
  CardContent,
  Typography,
  CardActions,
} from "@mui/material";
import Card from "@mui/material/Card";
import Cookies from "js-cookie";
import DailyBarchart from "../DailyBarchart/DailyBarchart";

const ActivityForm = () => {
  const [duration, setDuration] = useState(0);
  const [activityType, setActivityType] = useState();
  const [searchedActivity, setSearchedActivity] = useState("");
  const [isSearched, setIsSearched] = useState(true);
  const [foundActivityTypes, setFoundActivityTypes] = useState([]);
  const [open, setOpen] = React.useState(false);
  const jwtToken = Cookies.get("jwtToken");
  const [dailyCalorieBalanceInfos, setDailyCalorieBalanceInfos] = useState([
    {
      requiedCalorie: 0,
      dailyCalorieConsumption: 0,
      dailyActivityCalorie: 0,
    },
  ]);
  const [filterDuration, setFilterDuration] = useState("daily");
  const [flexDirection, setFlexDirection] = useState("row");
  const [snackbarError, setSnackbarError] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);

  useEffect(() => {
    fetchDailyCalories().then((listedMeals) => {
      setDailyCalorieBalanceInfos(listedMeals);
    });
  }, []);

  const fetchDailyCalories = () => {
    return fetch(`/analyze/?duration=${filterDuration}`, {
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
      setFlexDirection("column");
    } else {
      setFlexDirection("row");
    }
  };

  //Notify parts
  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }

    setOpen(false);
  };

  const handleCaloriesChange = (event) => {
    setDuration(event.target.value);
  };

  const handleSelectActivityType = (selectedActivityType) => {
    setActivityType(selectedActivityType);
    setIsSearched(false);
  };

  const fetchActivityType = () => {
    return fetch(`/activitytype/search?&activityType=${searchedActivity}`, {
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
    fetchActivityType().then((listedActivityTypes) => {
      setIsSearched(true);
      setFoundActivityTypes(listedActivityTypes);
    });
  };

  const handleSubmit = async (event) => {
    setSuccessMessage(null);
    setSnackbarError(null);
    event.preventDefault();
    if (duration <= 0) {
      setOpen(true);
      setSnackbarError("Amount must be greater than zero");
      return;
    }
    try {
      const response = await fetch("/activities/", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${jwtToken}`,
        },
        body: JSON.stringify({
          activityTypeId: activityType.id,
          duration: duration,
        }),
      });

      if (!response.ok) {
        setOpen(true);
        setSnackbarError("Failed to post calories.");
        throw new Error("Failed to post activity.");
      }
      setOpen(true);
      setSuccessMessage("Posted meal");
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
      <Box
        flex={5}
        sx={{
          display: "flex",
          flexDirection: { flexDirection },
          justifyContent: "space-between",
          p: 1,
          m: 1,
          bgcolor: "background.paper",
          borderRadius: 1,
        }}
      >
        {isSearched ? (
          <Box>
            <TextField
              sx={{
                width: "100%",
              }}
              id="search-activity"
              label="Search activity"
              type="search"
              value={searchedActivity}
              onChange={(event) => setSearchedActivity(event.target.value)}
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
            {foundActivityTypes !== undefined ? (
              <Table sx={{ width: "100%" }}>
                {foundActivityTypes.length !== 0 ? (
                  <TableHead>
                    <TableRow>
                      <TableCell>Activity Type (1 minute activity)</TableCell>
                      <TableCell>Calorie</TableCell>
                    </TableRow>
                  </TableHead>
                ) : null}
                <TableBody>
                  {foundActivityTypes.map((activitytype) => (
                    <TableRow
                      sx={{ cursor: "pointer" }}
                      key={activitytype.id}
                      onClick={() => handleSelectActivityType(activitytype)}
                    >
                      <TableCell>{activitytype.name}</TableCell>
                      <TableCell>{activitytype.calorie}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            ) : (
              <h6>No such activity found</h6>
            )}
          </Box>
        ) : (
          <Box>
            <TextField
              id="outlined-search"
              label="Search food"
              type="search"
              fullWidth
              value={searchedActivity}
              onChange={(event) => setSearchedActivity(event.target.value)}
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
            <form className="activity-form" onSubmit={handleSubmit}>
              <TextField
                type="number"
                style={{ width: "345px" }}
                id="outlined-basic"
                label="Amount"
                variant="outlined"
                onChange={handleCaloriesChange}
                value={duration}
                sx={{ mb: 2 }}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">minutes</InputAdornment>
                  ),
                }}
              />
              <Card sx={{ maxWidth: 345, mb: 2 }}>
                <CardMedia
                  sx={{ height: 140 }}
                  image="https://media.istockphoto.com/id/1285465265/hu/fot%C3%B3/mosolyg%C3%B3s-gy%C3%B6ny%C3%B6r%C5%B1-csal%C3%A1d-n%C3%A9gy-play-fetch-rep%C3%BCl%C5%91-lemez-boldog-golden-retriever-dog-a-backyard.jpg?s=612x612&w=0&k=20&c=-fD9_qmcl4GYR48RuL3mBBXB4kkJ16SiylvW-8qH7xg="
                  title="activity"
                />
                <CardContent>
                  <Typography gutterBottom variant="h5" component="div">
                    {activityType.name}
                  </Typography>
                  <Typography variant="body2" color="text.secondary">
                    One minute of {activityType.name} burns{" "}
                    {activityType.calorie} calories.
                  </Typography>
                </CardContent>
              </Card>
              <Button
                variant="contained"
                type="submit"
                style={{ minWidth: "345px" }}
              >
                Post Activity
              </Button>

              <Notification
                snackbarOpen={open}
                onClose={handleClose}
                successMassage={successMessage}
                snackbarError={snackbarError}
              />
            </form>
          </Box>
        )}
        <Box>
          <DailyBarchart
            todayBalance={dailyCalorieBalanceInfos}
          ></DailyBarchart>
        </Box>
      </Box>
    </>
  );
};

export default ActivityForm;
