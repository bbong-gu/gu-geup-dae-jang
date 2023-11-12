import { useState, ChangeEvent } from 'react';
import { useNavigate } from 'react-router-dom';

import * as S from './LoginInput.style';
import A from '../../Commons/Atoms';
import theme from '/src/styles';
import PATH from '/src/constants/path';
import LoginFailModal from '../../Commons/Molecules/LoginFailModal/LoginFailModal';
import Spinner from '../../libraries/Spinner/Spinner';

import { deleteLogout, postLogin } from '/src/apis/auth';
import { LoginProps } from '/src/types/auth';

import { useResetRecoilState, useSetRecoilState } from 'recoil';
import { hospitalInfoState, memberInfoState } from '/src/recoils/AuthAtoms';

function LoginInput() {
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const resetHospitalInfoState = useResetRecoilState(hospitalInfoState);
  const setMemberInfo = useSetRecoilState(memberInfoState);
  const [isOpen, setIsOpen] = useState(false);
  const [showSpinner, setShowSpinner] = useState(false);
  const [modalContent, setModalContent] = useState('');

  const MAX_LENGTH = 50;

  const navigate = useNavigate();
  const goSignUp = () => navigate(PATH.Signup);
  const goHospital = () => navigate(PATH.Hospital);
  const goParamedic = () => navigate(PATH.Paramedic);

  const handleEmail = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.value.length > MAX_LENGTH) {
      e.target.value = e.target.value.slice(0, MAX_LENGTH);
    }
    setEmail(e.target.value.split(' ').join(''));
  };

  const handlePassword = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.value.length > MAX_LENGTH) {
      e.target.value = e.target.value.slice(0, MAX_LENGTH);
    }
    setPassword(e.target.value.split(' ').join(''));
  };

  const handleEnterPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      if (isOpen) {
        setIsOpen(false);
      } else {
        axiosLogin();
      }
    }
  };

  const axiosLogin = async (): Promise<void> => {
    if (showSpinner) return;
    setShowSpinner(true);
    if (email.length < 1) {
      setModalContent('이메일을 입력해주세요.');
      setIsOpen(true);
      setShowSpinner(false);
      return;
    }
    if (password.length < 1) {
      setModalContent('비밀번호를 입력해주세요.');
      setIsOpen(true);
      setShowSpinner(false);
      return;
    }
    const info: LoginProps = {
      email: email,
      password: password,
    };
    try {
      const response = await postLogin(info);
      setMemberInfo(response);
      if (response.role === 'PARAMEDIC') {
        goParamedic();
      } else if (response.role === 'HOSPITAL') {
        resetHospitalInfoState();
        goHospital();
      }
    } catch (error) {
      setModalContent('회원 정보가 맞지 않습니다.');
      setIsOpen(true);
      setPassword('');
      console.log(error);
    }
    setShowSpinner(false);
  };

  const axiosLogout = async (): Promise<void> => {
    try {
      const response = await deleteLogout();
      if (response === 200) {
        localStorage.removeItem('access_token');
        localStorage.removeItem('refresh_token');
        console.log('로그아웃 완료');
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <S.Container onKeyDown={handleEnterPress}>
      {isOpen && <LoginFailModal content={modalContent} setIsOpen={setIsOpen} />}

      <S.Row1>
        <A.IptUserInfo
          type="email"
          $width="70%"
          $height="100%"
          placeholder="이메일"
          value={email}
          onChange={handleEmail}
        />
      </S.Row1>

      <S.Row1>
        <A.IptUserInfo
          type="password"
          $width="70%"
          $height="100%"
          placeholder="비밀번호"
          value={password}
          onChange={handlePassword}
        />

        <A.BtnSubmit
          $margin="0 0 0 auto"
          $width="20%"
          $height="100%"
          $fontSize="2vh"
          $borderRadius="1vh"
          $backgroundColor={theme.color.fontPink1}
          onClick={() => axiosLogin()}
        >
          {showSpinner ? <Spinner width="8vh" height="8vh" top="61.6vh" /> : '로그인'}
        </A.BtnSubmit>
      </S.Row1>

      <S.Row2>
        <S.TxtLoginToggle onClick={goSignUp}>회원가입</S.TxtLoginToggle>
        <S.TxtLoginToggle>/</S.TxtLoginToggle>
        <S.TxtLoginToggle>비밀번호 찾기</S.TxtLoginToggle>
      </S.Row2>

      {/* 삭제할 예정 */}
      <S.Row1>
        <A.BtnSubmit
          $margin="0 0 0 auto"
          $width="30%"
          $height="100%"
          $fontSize="2vh"
          $borderRadius="1vh"
          $backgroundColor={theme.color.fontPink1}
          onClick={() => axiosLogout()}
        >
          임시 로그아웃
        </A.BtnSubmit>
      </S.Row1>
    </S.Container>
  );
}

export default LoginInput;
