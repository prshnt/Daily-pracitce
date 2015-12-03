<?php

/**
 * Created by PhpStorm.
 * User: zzt
 * Date: 11/29/15
 * Time: 7:13 PM
 */
abstract class Controller
{
    const FUNC_NAME = "funcName";
    const USER_NAME = "uname";
    const DEFAULT_AJAX_RETURN = 'JSON';
    const LOGGEDIN = 'loggedin';
    const UID = 'uid';
    const SEPARATOR = "\n";

    /**
     * Controller constructor.
     */
    public function __construct()
    {
        session_start();
        if (isset($_SESSION[self::LOGGEDIN]) && $_SESSION[self::LOGGEDIN] == true) {
//            echo "Welcome to the member's area, " . $_SESSION[self::USER_NAME] . "!";
        } else {
//            echo "Please log in first to see this page.";
            echo "../html/login.php";
//            http_response_code(302);
        }
    }

    public function makeSession(array $user)
    {
        session_start();
        $_SESSION[self::LOGGEDIN] = true;
        $_SESSION[self::USER_NAME] = $user['uname'];
        $_SESSION[self::UID] = $user['uid'];
    }

    public function ajaxReturn($data, $type = '', $json_option = 0)
    {
        if (is_null($data)) {
            return;
        }
        if (is_object($data)) {
            $data = (array)$data;
        }
        if (empty($type)) {
            $type = self::DEFAULT_AJAX_RETURN;
        }
        switch (strtoupper($type)) {
            case 'JSON' :
                $data = str_replace('\\u0000', "", json_encode($data));
                break;
            case 'EVAL' :
                break;
        }
        echo($data);
    }


    function distribute() {
        // print_r($_POST);
        // print_r(debug_backtrace());
        if (isset($_POST[Controller::FUNC_NAME])) {
            $f = $_POST[Controller::FUNC_NAME];
        } else {
            $f = $_GET[Controller::FUNC_NAME];
        }
        $this->$f();
    }

}