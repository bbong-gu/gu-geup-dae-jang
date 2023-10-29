import { createBrowserRouter } from 'react-router-dom';
import Root from './Root';
import Home from './pages/Home';
import ErrorComponent from './components/ErrorComponent';
import NotFound from './pages/NotFound';
import PATH from './constants/path';
import PAGE from './constants/page';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Root />,
    children: [
      {
        path: '',
        element: <Home />,
        errorElement: <ErrorComponent />,
      },
      {
        path: PATH.Login,
        element: <PAGE.Login />,
        errorElement: <ErrorComponent />,
      },
      {
        path: PATH.Signup,
        element: <PAGE.Signup />,
        errorElement: <ErrorComponent />,
      },
      {
        path: PATH.Hospital,
        element: <PAGE.Hospital />,
        errorElement: <ErrorComponent />,
      },
      {
        path: PATH.HospitalSocket,
        element: <PAGE.HospitalSocket paramedicId="1" />,
        errorElement: <ErrorComponent />,
      },
      {
        path: PATH.HospitalHistory,
        element: <PAGE.HospitalHistory/>,
        errorElement: <ErrorComponent />,
      },
      {
        path: PATH.HospitalStatistic,
        element: <PAGE.HospitalStatistic/>,
        errorElement: <ErrorComponent />,
      },
      {
        path: PATH.Paramedic,
        element: <PAGE.Paramedic />,
        errorElement: <ErrorComponent />,
      },
      {
        path: PATH.ParamedicCall,
        element: <PAGE.ParamedicCall />,
        errorElement: <ErrorComponent />,
      },
      {
        path: PATH.ParamedicHistory,
        element: <PAGE.HospitalHistory />,
        errorElement: <ErrorComponent />,
      },
      {
        path: PATH.ParamedicStatistic,
        element: <PAGE.ParamedicStatistic />,
        errorElement: <ErrorComponent />,
      },
      {
        path: PATH.ParamedicWaitMove,
        element: <PAGE.ParamedicWaitMove />,
        errorElement: <ErrorComponent />,
      },
      {
        path: PATH.ParamedicSocket,
        element: <PAGE.ParamedicSocket hospitalId="1" />,
        errorElement: <ErrorComponent />,
      },
      {
        path: PATH.Guest,
        element: <PAGE.Guest/>,
        errorElement: <ErrorComponent />,
      },
    ],
    errorElement: <NotFound />,
  },
]);
export default router;
