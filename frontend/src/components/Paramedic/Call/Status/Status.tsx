import { useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { recordContentFile  } from '/src/recoils/ParamedicAtoms';
import { CallProps } from '/src/types/paramedic';
import * as S from './Status.style';
import A from '/src/components/Commons/Atoms';
import theme from '/src/styles';
import Spinner from '/src/components/libraries/Spinner/Spinner';

function Status({ RecordStart, CameraOpen }: CallProps) {
  const [recordContent, setRecordContent] = useRecoilState(recordContentFile);

  return (
    <S.Status>
      <A.TxtParamedicTitle>환자 상태</A.TxtParamedicTitle>
      <S.Col9>
        <A.BtnMediaRecord
          $padding="0 1.5vh"
          $width="48%"
          $height="6.6vh"
          $color={theme.color.pinkLight}
          $border={`0.3vh solid ${theme.color.pinkLight}`}
          $borderRadius="1.8vh"
          $boxShadow="0 0 1vh 0.4vh rgba(0, 0, 0, 0.10)"
          $fontSize="2.2vh"
          onClick={() => {
            CameraOpen?.();
          }}
        >
          <A.ImgRecordCameraPink $width="3.2vh" />
          카메라 촬영
          <A.ImgArrowPinkRight $width="1vh" />
        </A.BtnMediaRecord>

        <A.BtnMediaRecord
          $padding="0 1vh"
          $width="48%"
          $height="6.6vh"
          $color={theme.color.pinkLight}
          $border={`0.3vh solid ${theme.color.pinkLight}`}
          $borderRadius="1.8vh"
          $boxShadow="0 0 1vh 0.4vh rgba(0, 0, 0, 0.10)"
          $fontSize="2.2vh"
          onClick={() => {
            RecordStart?.();
          }}
        >
          <A.ImgRecordVoicePink $width="2.2vh" />
          음성 녹음
          <A.ImgArrowPinkRight $width="1vh" />
        </A.BtnMediaRecord>

        <S.TxtStatus
          value={recordContent}
          onChange={(e) => setRecordContent(e.target.value)}
          placeholder="환자의 상태를 상세히 입력해주세요."
        >
        </S.TxtStatus>
      </S.Col9>
    </S.Status>
  );
}

export default Status;
