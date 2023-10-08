import { useState, useRef, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faCheck,
    faTimes,
    faInfoCircle,
} from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';
import './SignupForm.css';
import { Box } from '@mui/material';

const USER_REGEX = /^[A-z][A-z0-9-_]{3,23}$/;
//const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const PWD_REGEX = /^([a-z])/;
const REGISTER_URL = '/register';

const createNewUser = (tempObj) => {
    return fetch('/api/v1/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(tempObj),
    }).then((res) => {                 console.log(tempObj);
        res.json()});
};

const SignupForm = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const userRef = useRef();
    const errRef = useRef();

    const [user, setUser] = useState('');
    const [validName, setValidName] = useState(false);
    const [userFocus, setUserFocus] = useState(false);

    const [email, setEmail] = useState('');

    const [pwd, setPwd] = useState('');
    const [validPwd, setValidPwd] = useState(false);
    const [pwdFocus, setPwdFocus] = useState(false);

    const [matchPwd, setMatchPwd] = useState('');
    const [validMatch, setValidMatch] = useState(false);
    const [matchFocus, setMatchFocus] = useState(false);

    const [errMsg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false);
    const [sendButtonDisabled, setSendButtonDisabled] = useState(false);

    useEffect(() => {
        userRef.current.focus();
    }, []);

    useEffect(() => {
        const result = USER_REGEX.test(user);
        setValidName(result);
    }, [user]);

    useEffect(() => {
        const result = PWD_REGEX.test(pwd);
        setValidPwd(result);
        const match = pwd === matchPwd;
        setValidMatch(match);
    }, [pwd, matchPwd]);

    useEffect(() => {
        setErrMsg('');
    }, [user, pwd, matchPwd]);

    const onSubmit = (e) => {
        e.preventDefault();
        const v1 = USER_REGEX.test(user);
        const v2 = PWD_REGEX.test(pwd);
        if (!v1 || !v2) {
            setErrMsg('Invalid Entry');
            return;
        }
        setSendButtonDisabled(true);

        const tempObj = {
            "username": user,
            "email": email,
            "password": pwd,
        };

        createNewUser(tempObj)
            .then((result) => {
                const emailAlreadyInUse = result === false;
                if (emailAlreadyInUse) {
                    setSendButtonDisabled(false);
                    setErrMsg('This email address has already taken!');
                } else {
                    setSuccess(true);
                    
                    setUser('');
                    setPwd('');
                    setMatchPwd('');
                    setTimeout(() => {
                        navigate('/login');
                    }, 2000);
                }
            })
            .catch((err) => {
            });
    };

    return (
        <>
            <Box flex={9} p={{ xs: 0, md: 2 }}>
                {success ? (
                    <section>
                        {/* 
                            loading
                        */}
                    </section>
                ) : (
                    <section>
                        <p
                            ref={errRef}
                            className={errMsg ? 'errmsg' : 'offscreen'}
                            aria-live='assertive'
                        >
                            {errMsg}
                        </p>
                        <form className='SignupForm' onSubmit={onSubmit}>
                            <label htmlFor='userName'>
                                Username:
                                <FontAwesomeIcon
                                    icon={faCheck}
                                    className={validName ? 'valid' : 'hide'}
                                />
                                <FontAwesomeIcon
                                    icon={faTimes}
                                    className={
                                        validName || !user ? 'hide' : 'invalid'
                                    }
                                />
                            </label>
                            <input
                                type='text'
                                id='userName'
                                ref={userRef}
                                autoComplete='off'
                                onChange={(e) => setUser(e.target.value)}
                                value={user}
                                required
                                aria-invalid={validName ? 'false' : 'true'}
                                aria-describedby='uidnote'
                                onFocus={() => setUserFocus(true)}
                                onBlur={() => setUserFocus(false)}
                            />
                            <p
                                id='uidnote'
                                className={
                                    userFocus && user && !validName
                                        ? 'instructions'
                                        : 'offscreen'
                                }
                            >
                                <FontAwesomeIcon icon={faInfoCircle} />
                                4 to 24 characters.
                                <br />
                                Must begin with a letter.
                                <br />
                                Letters, numbers, underscores, hyphens allowed.
                            </p>

                            <label htmlFor='email'>Email:</label>
                            <input
                                type='email'
                                onChange={(e) => setEmail(e.target.value)}
                            />

                            <label htmlFor='password'>
                                Password:
                                <FontAwesomeIcon
                                    icon={faCheck}
                                    className={validPwd ? 'valid' : 'hide'}
                                />
                                <FontAwesomeIcon
                                    icon={faTimes}
                                    className={
                                        validPwd || !pwd ? 'hide' : 'invalid'
                                    }
                                />
                            </label>
                            <input
                                type='password'
                                id='password'
                                onChange={(e) => setPwd(e.target.value)}
                                value={pwd}
                                required
                                aria-invalid={validPwd ? 'false' : 'true'}
                                aria-describedby='pwdnote'
                                onFocus={() => setPwdFocus(true)}
                                onBlur={() => setPwdFocus(false)}
                            />
                            <p
                                id='pwdnote'
                                className={
                                    pwdFocus && !validPwd
                                        ? 'instructions'
                                        : 'offscreen'
                                }
                            >
                                <FontAwesomeIcon icon={faInfoCircle} />
                                8 to 24 characters.
                                <br />
                                Must include uppercase and lowercase letters, a
                                number and a special character.
                                <br />
                                Allowed special characters:{' '}
                                <span aria-label='exclamation mark'>
                                    !
                                </span>{' '}
                                <span aria-label='at symbol'>@</span>{' '}
                                <span aria-label='hashtag'>#</span>{' '}
                                <span aria-label='dollar sign'>$</span>{' '}
                                <span aria-label='percent'>%</span>
                            </p>

                            <label htmlFor='confirm_pwd'>
                                Confirm Password:
                                <FontAwesomeIcon
                                    icon={faCheck}
                                    className={
                                        validMatch && matchPwd
                                            ? 'valid'
                                            : 'hide'
                                    }
                                />
                                <FontAwesomeIcon
                                    icon={faTimes}
                                    className={
                                        validMatch || !matchPwd
                                            ? 'hide'
                                            : 'invalid'
                                    }
                                />
                            </label>
                            <input
                                type='password'
                                id='confirm_pwd'
                                onChange={(e) => setMatchPwd(e.target.value)}
                                value={matchPwd}
                                required
                                aria-invalid={validMatch ? 'false' : 'true'}
                                aria-describedby='confirmnote'
                                onFocus={() => setMatchFocus(true)}
                                onBlur={() => setMatchFocus(false)}
                            />
                            <p
                                id='confirmnote'
                                className={
                                    matchFocus && !validMatch
                                        ? 'instructions'
                                        : 'offscreen'
                                }
                            >
                                <FontAwesomeIcon icon={faInfoCircle} />
                                Must match the first password input field.
                            </p>

                            <button
                                disabled={
                                    sendButtonDisabled &&
                                    (!validName || !validPwd || !validMatch)
                                        ? true
                                        : false
                                }
                            >
                                Sign Up
                            </button>
                        </form>
                    </section>
                )}
            </Box>
        </>
    );
};

export default SignupForm;
