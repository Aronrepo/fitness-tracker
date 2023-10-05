import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import ErrorPage from "./Pages/ErrorPage"
import About from './components/About';
import CalorieForm from './components/CalorieForm';
import Home from './components/Home';
import CalorieDailyList from './components/CalorieDailyList';
import ActivityForm from './components/Activity/ActivityForm';
import ActivityDailyList from './components/Activity/ActivityDailyList';
import LoginForm from './components/LoginForm';
import SignupForm from './components/SignupForm';
import Profile from './components/Profile';
import Analyze from './components/Analyze';


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


