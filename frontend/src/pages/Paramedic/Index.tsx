import { useRecoilValue } from 'recoil';
import { currentParamedicPageIndexState } from '/src/recoils/ParamedicAtoms';
import Geolocation from '/src/components/libraries/Geolocation/Geolocation';
import ParamedicSocket from '/src/sockets/ParamedicSocket';
import Main from './Main/Main';
import Call from './Call/Call';
import WaitMove from './WaitMove/WaitMove';
import History from './History/History';
import Statistic from './Statistics/Statistic';
import { useEffect } from 'react';

const paramedicPages: any = {
  0: <Main />,
  1: <Call />,
  2: <WaitMove />,
  3: <History />,
  4: <Statistic />,
};

function Index() {
  const currentPageIndex = useRecoilValue(currentParamedicPageIndexState);

  return (
    <>
      <Geolocation />
      <ParamedicSocket />
      {paramedicPages[currentPageIndex]}
    </>
  );
}

export default Index;
