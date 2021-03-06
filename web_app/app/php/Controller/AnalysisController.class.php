<?php
/**
 * Created by PhpStorm.
 * User: zzt
 * Date: 12/2/15
 * Time: 9:56 AM
 */

// place this at the top of the file
if (count(get_included_files()) == 1) {
    define('TEST_SUITE', __FILE__);
}

require_once 'includes.php';


class AnalysisController extends Controller
{
    private $HGMapper;
    private $HSMapper;
    private $planMapper;

    /**
     * AnalysisController constructor.
     */
    public function __construct()
    {
        parent::__construct();
        $this->HGMapper = new HGMapper();
        $this->HSMapper = new HSMapper();
        $this->planMapper = new PlanMapper();
    }

    public function getAnalysisData()
    {
        $uid = $_SESSION[Controller::UID];
//        echo $uid;
        $healthGoal = $this->HGMapper->findByKey($uid);
        $healthStatistic = $this->HSMapper->findByKey($uid);
        $plan = $this->planMapper->findByKey($uid);
        $this->ajaxReturn($healthGoal);
        echo Controller::SEPARATOR;
        $this->ajaxReturn($healthStatistic);
        echo Controller::SEPARATOR;
        $this->ajaxReturn($plan);
    }

    public function editPlan()
    {
        $countPlan = $this->planMapper->countPlan()[PlanMapper::COUNT];
//        print_r($_POST);
        $pid = date('w') % $countPlan;
//        echo $countPlan;
        $res = $this->planMapper->update(
            array(
                $_POST['breakfast'], $_POST['lunch'],
                $_POST['dinner'], $_POST['exercise']
            ),
            array(
                $_SESSION[Controller::UID],
                $pid
            )
        );
        if ($this->planMapper->isSuccess($res, $this->planMapper->updateStmt())) {
            echo 'true';
        } else {
            echo 'false';
        }
    }

    public function addPlan()
    {
        $countPlan = $this->planMapper->countPlan()[PlanMapper::COUNT];
        $res = $this->planMapper->insert(
            array(
                $_SESSION[Controller::UID],
                $countPlan + 1
            )
        );
        $res = $this->planMapper->update(
            array(
                $_POST['breakfast'], $_POST['lunch'],
                $_POST['dinner'], $_POST['exercise']
            ),
            array(
                $_SESSION[Controller::UID],
                $countPlan + 1
            )
        );
        if ($this->planMapper->isSuccess($res, $this->planMapper->updateStmt())) {
            echo 'true';
        }
        echo 'false';
    }

    public function editAim()
    {
        $this->HGMapper->update(array(
            $_POST['walk'], $_POST['upper_limb'], $_POST['lower_limb']
        ), $_SESSION[Controller::UID]);
    }

    /**
     * read data from xml file and insert HSMapper
     */
    public function readTodayData()
    {
        $xml = new xmlDataReader("../../data/2015-12-02");
        $data = $xml->getAllUnderUser();
//        print_r((array)$data);

        try {
            $this->HSMapper->insert($data);
        } catch (Exception $e) {
        }
        $this->ajaxReturn($data);
    }
}


if (defined('TEST_SUITE') && TEST_SUITE == __FILE__) {
    // run test suite here
    $analysis = new AnalysisController();
    $analysis->distribute();
}