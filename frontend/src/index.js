import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import ErrorPage from "./Pages/Error/ErrorPage"
import About from './Pages/About/About';
import CalorieForm from './Pages/CalorieInput/CalorieForm';
import Home from './Pages/Home/Home';
import CalorieDailyList from './Pages/CalorieDaily/CalorieDailyList';
import ActivityForm from './components/Activity/ActivityForm';
import ActivityDailyList from './components/Activity/ActivityDailyList';
import LoginForm from './components/LoginForm';
import SignupForm from './components/SignupForm';
import Profile from './Pages/Profile/Profile';
import Analyze from './Pages/Analyze/Analyze';


const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "/about",
        element: <About />,
      },
      {
        path: "/",
        element: <Home />,
      },
      {
        path: "/calorie",
        element: <CalorieForm />,
      },
      {
        path: "/profile",
        element: <Profile />,
      },
      {
        path: "/yourDailyCalorie",
        element: <CalorieDailyList />,
      },
      {
        path: "/activity",
        element: <ActivityForm />,
      },
      {
        path: "/yourDailyActivity",
        element: <ActivityDailyList />,
      },
      {
        path: "/login",
        element: <LoginForm />,
      },
      {
        path: "/signup",
        element: <SignupForm />,
      },
      {
        path: "/analyzeUser",
        element: <Analyze />,
      },
      
  ],
}
]);
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>
);


